package com.sshProject.controller;

import com.sshProject.dao.*;
import com.sshProject.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private InworkspaceDao inworkspaceDao;

    @Autowired
    private WorkspaceDao workspaceDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private InchannelDao inchannelDao;

    @Autowired
    private JoinworkspaceDao joinworkspaceDao;

    @Autowired
    private JoinchannelDao joinchannelDao;

    @Autowired
    private MessageDao messageDao;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(HttpServletRequest request, String email, String name, String nickname, String password){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setNickname(nickname);
        user.setPassword(password);
        if (userDao.findUserByemail(email)!=null){

            return "FAILURE";

        }else {
            int userid = userDao.saveUser(user);
            if (userid!=-1){

                Workspace workspace = new Workspace();
                workspace.setUserid(userid);
                workspace.setDescription("");
                workspace.setName("default workspace");
                workspace.setCreatedate(new Date());
                int workspaceid = workspaceDao.saveWorkspace(workspace);
                Inworkspace inworkspace = new Inworkspace();
                inworkspace.setWorkspaceid(workspaceid);
                inworkspace.setParticipantid(userid);
                inworkspaceDao.addInworkspace(inworkspace);
                Admin admin = new Admin();
                admin.setUserid(userid);
                admin.setWorkspaceid(workspaceid);
                admin.setAdddate(new Date());
                adminDao.addAdmin(admin);
                System.out.println(userid+" "+workspaceid);
                return "SUCCESS";
            }else {
                System.out.println("Fail");
                return "FAILURE";
            }

        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, String username, String password){
        User user=userDao.findUserByemail(username);

        if (user==null||!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return "FAILURE";
        }else {
            List<Workspace> workspaces = workspaceDao.findWorkspaceByUserid(user.getUserid());
            request.getSession().setAttribute("userid",user.getUserid());
            request.getSession().setAttribute("workspaceid",workspaces.get(0).getWorkspaceid());
//            request.getSession().setAttribute("nickname",user.getNickname());
            return "SUCCESS";
        }

    }

    @RequestMapping(value = "/personalPage", method = RequestMethod.GET)
    public ModelAndView personalPage(HttpServletRequest request, Integer toworkspaceid){
        Object useridObject = request.getSession().getAttribute("userid");
        if (useridObject ==null){
            return new ModelAndView("login");
        }
        Object workspaceidObject =request.getSession().getAttribute("workspaceid");
        Integer workspaceid= null;
        if (workspaceidObject!=null){
            workspaceid= (Integer) workspaceidObject;
        }
        int userid = (int) useridObject;
        User user =userDao.findUserById(userid);
        List<Inworkspace> inworkspaces = inworkspaceDao.findByuserid(userid);
        List<Workspace> workspaces = new ArrayList<>();
        for (Inworkspace in:inworkspaces){
            Workspace workspace = workspaceDao.findByWorkspaceid(in.getWorkspaceid());
            if (workspace!=null){
                workspaces.add(workspace);
            }
        }
        ModelAndView modelAndView = new ModelAndView("personalPage");
        modelAndView.addObject("user",user);
        modelAndView.addObject("workspaces",workspaces);

        Integer finalworkspaceid = null;
        if (toworkspaceid!=null){
            finalworkspaceid=toworkspaceid;
        }else if (workspaceid!=null){
            finalworkspaceid =workspaceid;
        }else {

        }
        if (finalworkspaceid==null){
            modelAndView.addObject("workspace",workspaces.get(0));
            request.getSession().setAttribute("workspaceid",workspaces.get(0).getWorkspaceid());
        }else {
            System.out.println(request.getSession().getAttribute("workspaceid"));
            boolean belongsTo=false;
            for(Workspace w:workspaces){
                if (w.getWorkspaceid()==finalworkspaceid){
                    modelAndView.addObject("workspace",w);
                    belongsTo =true;
                    break;
                }
            }
            if (belongsTo == false){
                finalworkspaceid = workspaces.get(0).getWorkspaceid();
                modelAndView.addObject("workspace",workspaces.get(0));
            }

            request.getSession().setAttribute("workspaceid",finalworkspaceid);
        }


        List<Inchannel> inchannels = inchannelDao.findByParticipantid(userid);
        ArrayList<Channel> publicChannel = new ArrayList<>();
        ArrayList<Channel> directChannel = new ArrayList<>();
        ArrayList<Channel> privateChannel = new ArrayList<>();
        for(Inchannel inchannel:inchannels){
            Channel channel =channelDao.findByChannelid(inchannel.getChannelid());
            if (channel.getWorkspaceid()==finalworkspaceid){
                if (channel.getType().equals("public")){
                    publicChannel.add(channel);
                }else if (channel.getType().equals("direct")){
                    directChannel.add(channel);
                }else if (channel.getType().equals("private")){
                    privateChannel.add(channel);
                }
            }
        }

        modelAndView.addObject("publicChannel",publicChannel);
        modelAndView.addObject("directChannel",directChannel);
        modelAndView.addObject("privateChannel",privateChannel);

        return modelAndView;
    }


    @RequestMapping(value = "/createworkspace", method = RequestMethod.POST)
    @ResponseBody
    public String createworkspace(HttpServletRequest request, String name, String description){
        Object useridObject=request.getSession().getAttribute("userid");
        if (useridObject==null){
            return "login";
        }
        System.out.println("what?");
        int userid = (int) useridObject;
        Workspace workspace =new Workspace();
        workspace.setName(name);
        workspace.setDescription(description);
        workspace.setUserid(userid);
        workspace.setCreatedate(new Date());
        int workspaceid = workspaceDao.saveWorkspace(workspace);

        if (workspaceid!=-1){
            Inworkspace inworkspace = new Inworkspace();
            inworkspace.setWorkspaceid(workspaceid);
            inworkspace.setParticipantid(userid);
            inworkspaceDao.addInworkspace(inworkspace);
            Admin admin = new Admin();
            admin.setWorkspaceid(workspaceid);
            admin.setUserid(userid);
            admin.setAdddate(new Date());
            adminDao.addAdmin(admin);
            return "SUCCESS";
        }else {
            return "FAILURE";
        }
    }

    @RequestMapping(value = "/createchannel", method = RequestMethod.POST)
    @ResponseBody
    public String createchannel(HttpServletRequest request, String name, String type, Integer creatorid, Integer workspaceid){
        Object useridObject=request.getSession().getAttribute("userid");
        if (useridObject==null){
            return "login";
        }
        Channel channel = new Channel();
        channel.setCreatorid(creatorid);
        channel.setWorkspaceid(workspaceid);
        channel.setName(name);
        channel.setType(type);
        int channelid = channelDao.saveChannel(channel);
        if (channelid>=0){
            if (type.equals("public")){

                List<Inworkspace> inworkspaces = inworkspaceDao.findByWorkspaceid(workspaceid);
                System.out.println(workspaceid+"inworkspace"+inworkspaces.size());
                for(Inworkspace inworkspace:inworkspaces){
                    Inchannel inchannel =new Inchannel();
                    inchannel.setChannelid(channelid);
                    inchannel.setParticipantid(inworkspace.getParticipantid());
                    inchannelDao.addInchannel(inchannel);
                }
            }else {
                Inchannel inchannel =new Inchannel();
                inchannel.setChannelid(channelid);
                inchannel.setParticipantid(creatorid);
                inchannelDao.addInchannel(inchannel);
            }

            return "SUCCESS";
        }else {
            return "FAILURE";
        }
    }

    @RequestMapping(value = "/inviteworkspace", method = RequestMethod.POST)
    @ResponseBody
    public String inviteworkspace(HttpServletRequest request, String email, Integer inviterid, Integer workspaceid){
        Object useridObject=request.getSession().getAttribute("userid");
        if (useridObject==null){
            return "login";
        }
        List<Admin> admins = adminDao.findByWorkspaceid(workspaceid);
        boolean isAdmin =false;
        for (Admin admin:admins){
            if (admin.getUserid()==inviterid){
                isAdmin= true;
                break;
            }
        }
        if (isAdmin==false){
            return "Your are not authorized to do that";
        }

        User invitee = userDao.findUserByemail(email);

        if (invitee==null){
            return "the user doesn't exsit";
        }
        System.out.println("1113");
        if (inworkspaceDao.findByuseridAndworkspaceid(invitee.getUserid(),workspaceid)!=null){
            return "He has been in workspace already";
        }else {
            Joinworkspace joinworkspace = new Joinworkspace();
            joinworkspace.setInviterid(inviterid);
            joinworkspace.setInviteeid(invitee.getUserid());
            joinworkspace.setWorkspaceid(workspaceid);
            joinworkspace.setStatus("unread");
            joinworkspace.setInvitedate(new Date());
            int joinwsid= joinworkspaceDao.addJoinworkspace(joinworkspace);
            if (joinwsid>0){
                return "sending invitation succeed";
            }else {
                return "fail by other reason";
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("userid");
        return "login";
    }


    @RequestMapping(value = "/addadmin", method = RequestMethod.POST)
    @ResponseBody
    public String addadmin(HttpServletRequest request, String email, Integer inviterid, Integer workspaceid){
        Object useridObject=request.getSession().getAttribute("userid");
        if (useridObject==null){
            return "login";
        }
        List<Admin> admins = adminDao.findByWorkspaceid(workspaceid);
        boolean isAdmin =false;
        for (Admin admin:admins){
            if (admin.getUserid()==inviterid){
                isAdmin= true;
                break;
            }
        }
        if (isAdmin==false){
            return "Your are not authorized to do that";
        }

        User invitee = userDao.findUserByemail(email);

        if (invitee==null){
            return "the user doesn't exsit";
        }

        Inworkspace inworkspace = inworkspaceDao.findByuseridAndworkspaceid(invitee.getUserid(),workspaceid);
        if (inworkspace==null){
            return "the user doesn't belong to this workspace";
        }

        if (adminDao.findByUseridAndWorkspaceid(invitee.getUserid(),workspaceid)!=null){
            return "He has been an admin already";
        }else {
            Admin admin = new Admin();
            admin.setUserid(invitee.getUserid());
            admin.setWorkspaceid(workspaceid);
            boolean result = adminDao.addAdmin(admin);
            if (result==true){
                return "adding admin succeed";
            }else {
                return "fail by other reason";
            }
        }
    }

    @RequestMapping(value = "/notification", method = RequestMethod.GET)
    public ModelAndView notification(HttpServletRequest request) {
        Object useridObject = request.getSession().getAttribute("userid");
        if (useridObject == null) {
            return new ModelAndView("login");
        }
        int userid = (Integer) useridObject;
        List<Joinworkspace> joinworkspaces = joinworkspaceDao.findByInviteeid(userid);
        ArrayList<WorkspaceInvitation> workspaceInvitations = new ArrayList<>();
        for(Joinworkspace joinworkspace:joinworkspaces){
            if (joinworkspace.getStatus().equals("unread")){
                Workspace workspace = workspaceDao.findByWorkspaceid(joinworkspace.getWorkspaceid());
                User inviter= userDao.findUserById(joinworkspace.getInviterid());
                WorkspaceInvitation workspaceInvitation = new WorkspaceInvitation();

                workspaceInvitation.setJoinwsid(joinworkspace.getJoinwsid());
                workspaceInvitation.setInvitorname(inviter.getNickname());
                workspaceInvitation.setWorkspacename(workspace.getName());
                workspaceInvitation.setDescription(workspace.getDescription());
                workspaceInvitations.add(workspaceInvitation);
            }
        }

        List<Joinchannel> joinchannels =joinchannelDao.findByInviteeid(userid);
        ArrayList<ChannelInvitation> channelInvitations =new ArrayList<>();
        for(Joinchannel joinchannel:joinchannels){
            if (joinchannel.getStatus().equals("unread")){
                Channel channel =channelDao.findByChannelid(joinchannel.getChannelid());
                User invitor = userDao.findUserById(channel.getCreatorid());
                ChannelInvitation channelInvitation =new ChannelInvitation();
                channelInvitation.setName(channel.getName());
                channelInvitation.setInvitorname(invitor.getNickname());
                channelInvitation.setJoinchannelid(joinchannel.getJoinchannelid());
                channelInvitations.add(channelInvitation);
            }
        }

        System.out.println(channelInvitations.size()+" mamahaha "+workspaceInvitations.size());
        ModelAndView modelAndView = new ModelAndView("notification");
        modelAndView.addObject("workspaceInvitations",workspaceInvitations);
        modelAndView.addObject("channelInvitations",channelInvitations);
        return modelAndView;
    }

    @RequestMapping(value = "/updatejoinworkspace", method = RequestMethod.POST)
    public String updatejoinworkspace(HttpServletRequest request, Integer joinwsid, String agree_action, String reject_action) {
        Object useridObject = request.getSession().getAttribute("userid");
        if (useridObject == null) {
            return "login";
        }
        Joinworkspace joinworkspace =joinworkspaceDao.findByjoinwsid(joinwsid);
        if (agree_action!=null){
            boolean updatejoinworkspace = joinworkspaceDao.updateJoinworkspaceByStatus("agree",joinwsid);
            if (updatejoinworkspace==true){
                Inworkspace inworkspace = new Inworkspace();
                inworkspace.setParticipantid(joinworkspace.getInviteeid());
                inworkspace.setWorkspaceid(joinworkspace.getWorkspaceid());
                inworkspaceDao.addInworkspace(inworkspace);
            }
            List<Channel> channels = channelDao.findByWorkspaceid(joinworkspace.getWorkspaceid());
            for (Channel channel:channels){
                if (channel.getType().equals("public")){
                    Inchannel inchannel = new Inchannel();
                    inchannel.setChannelid(channel.getChannelid());
                    inchannel.setParticipantid(joinworkspace.getInviteeid());
                    inchannelDao.addInchannel(inchannel);
                }
            }
        }else if(reject_action!=null){
            boolean updatejoinworkspace = joinworkspaceDao.updateJoinworkspaceByStatus("reject",joinwsid);
        }

        return "redirect:/notification";
    }


    @RequestMapping(value = "/updatejoinchannel", method = RequestMethod.POST)
    public String updatejoinchannel(HttpServletRequest request, Integer joinchannelid, String agree_action, String reject_action) {
        Object useridObject = request.getSession().getAttribute("userid");
        if (useridObject == null) {
            return "login";
        }
        Joinchannel joinchannel =joinchannelDao.findByJoinchannelid(joinchannelid);
        if (agree_action!=null){
            boolean updatejoinchannel = joinchannelDao.updateJoinchannelStatus("agree",joinchannelid);
            if (updatejoinchannel==true){
                Inchannel inchannel =new Inchannel();
                inchannel.setChannelid(joinchannel.getChannelid());
                inchannel.setParticipantid(joinchannel.getInviteeid());
                inchannelDao.addInchannel(inchannel);
            }
        }else if(reject_action!=null){
            boolean updatejoinchannel = joinchannelDao.updateJoinchannelStatus("reject",joinchannelid);
        }

        return "redirect:/notification";
    }


    @RequestMapping(value = "/showchannel", method = RequestMethod.POST)
    @ResponseBody
    public String showchannel(HttpServletRequest request, Integer channelid) {
        List<Message> messages=messageDao.findByChannelid(channelid);
        JSONArray jsonArray =new JSONArray();
        for(Message message:messages){
            User user = userDao.findUserById(message.getSenderid());
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("nickname",user.getNickname());
            jsonObject.put("senddate",message.getSenddate().toString());
            jsonObject.put("content",message.getContent());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
    @ResponseBody
    public void sendmessage(HttpServletRequest request, String content,Integer channelid,Integer userid) {
        Message message = new Message();
        message.setChannelid(channelid);
        message.setContent(content);
        message.setSenderid(userid);
        message.setSenddate(new Date());
        messageDao.saveMessage(message);
    }


    @RequestMapping(value = "/invitepublicchannel", method = RequestMethod.POST)
    @ResponseBody
    public String invitepublicchannel(HttpServletRequest request, String email,Integer invitorid,Integer channelid, Integer workspaceid) {
        try {
            System.out.println(email+" "+invitorid+" "+channelid+" "+workspaceid+"aa");
            Channel channel = channelDao.findByChannelid(channelid);
            if (channel.getCreatorid() != invitorid) {
                return "you are not authorized to make invitation";
            }
            User user = userDao.findUserByemail(email);
            if (user == null) {
                return "no such person";
            }

            Inworkspace inworkspace = inworkspaceDao.findByuseridAndworkspaceid(user.getUserid(), workspaceid);
            if (inworkspace == null) {
                return "the person doesn't belong to the workspace";
            }

            List<Inchannel> inchannels = inchannelDao.findByParticipantid(user.getUserid());
            for (Inchannel in : inchannels) {
                if (in.getChannelid() == channelid) {
                    return "the person has been in the channel";
                }
            }

            System.out.println(email+" "+invitorid+" "+channelid+" "+workspaceid+"aa");
            Joinchannel joinchannel = new Joinchannel();
            joinchannel.setChannelid(channelid);
            joinchannel.setInvitedate(new Date());
            joinchannel.setInviteeid(user.getUserid());
            joinchannel.setStatus("unread");
            int joinchannelid = joinchannelDao.saveJoinchannel(joinchannel);
            if (joinchannelid > 0) {
                return "send invitation succeed";
            } else {
                return "failed";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
    }


    @RequestMapping(value = "/invitedirectchannel", method = RequestMethod.POST)
    @ResponseBody
    public String invitedirectchannel(HttpServletRequest request, String email,Integer invitorid,Integer workspaceid) {
        try {
            System.out.println(email+" "+invitorid+" "+" "+workspaceid+"aa");

            User invitee = userDao.findUserByemail(email);
            User invitor = userDao.findUserById(invitorid);
            if (invitee == null) {
                return "no such person";
            }

            Inworkspace inworkspace = inworkspaceDao.findByuseridAndworkspaceid(invitee.getUserid(), workspaceid);
            if (inworkspace == null) {
                return "the person doesn't belong to the workspace";
            }

//            List<Channel> channels =channelDao.findByWorkspaceid(workspaceid);
//            List<Inchannel> inchannels = inchannelDao.findByParticipantid(invitee.getUserid());
//            for(Channel channel:channels){
//                if (channel.getType().equals("direct")){
//                    for(Inchannel inchannel:inchannels){
//                        if (inchannel.getChannelid()==channel.getChannelid()){
//                            return "the direct exists. It is " + channel.getName();
//                        }
//                    }
//                }
//            }

            Channel channel =new Channel();
            channel.setName(invitor.getName()+"&"+invitee.getName());
            channel.setWorkspaceid(workspaceid);
            channel.setType("direct");
            channel.setCreatorid(invitorid);
            channel.setCreatedate(new Date());
            channelDao.saveChannel(channel);


            int channelid = channelDao.saveChannel(channel);
            Inchannel inchannel1 =new Inchannel();
            inchannel1.setChannelid(channelid);
            inchannel1.setParticipantid(invitorid);
            inchannelDao.addInchannel(inchannel1);

            Inchannel inchannel2 =new Inchannel();
            inchannel2.setChannelid(channelid);
            inchannel2.setParticipantid(invitee.getUserid());
            inchannelDao.addInchannel(inchannel2);
            return "create the channel succeed";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }
    }

    @RequestMapping(value = "/shownotificationnumber", method = RequestMethod.POST)
    @ResponseBody
    public int shownotificationnumber(HttpServletRequest request, Integer workspaceid,Integer userid) {
        int result=0;
        List<Joinchannel> joinchannels = joinchannelDao.findByInviteeid(userid);
        List<Joinworkspace> joinworkspaces = joinworkspaceDao.findByInviteeid(userid);
        for (Joinchannel joinchannel:joinchannels){
            if (joinchannel.getStatus().equals("unread")){
                    result++;
            }
        }

        for(Joinworkspace joinworkspace: joinworkspaces){
            if (joinworkspace.getStatus().equals("unread")){
                result++;
            }
        }
        return result;
    }
}
