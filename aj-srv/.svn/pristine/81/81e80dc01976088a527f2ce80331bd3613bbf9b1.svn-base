/**   
* @Package com.otos.msg.interfaces.impl
* @Description: 
* @author Wang Xue Feng   
* @date 2015年4月28日 下午4:39:57
* @version V1.0   
*/


package com.aj.msg.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.aam.model.TCustom;
import com.aj.msg.api.jpush.JPush;
import com.aj.msg.api.jpush.JPushConfig.JPushPlatformType;
import com.aj.msg.api.jpush.JPushConfig.JPushUserType;
import com.aj.msg.model.TMsgInfo;
import com.aj.msg.model.TMsgSendLog;
import com.aj.msg.model.VEventMsgInfo;
import com.aj.msg.service.MessageLogService;
import com.aj.msg.service.MessageService;
import com.aj.msg.service.impl.sql.TMessageSQL;
import com.aj.msg.service.impl.sql.TMessageSQLData;
import com.aj.msg.vo.MsgLimitKey;
import com.aj.msg.vo.PushMessageParamVo;
import com.aj.msg.vo.SentMessageVo;
import com.aj.msg.vo.SentMessageVo.SentStatus;
import com.frame.core.dao.GenericDAO;
import com.frame.core.exception.ServiceException;
import com.frame.core.service.AuthorService;
import com.frame.core.service.impl.PageServiceImpl;
import com.frame.core.util.BeanUtils;
import com.frame.core.util.DateUtil;
import com.frame.core.util.GUID;
import com.frame.core.util.GenerateSequenceUtil;
import com.frame.core.util.StringUtil;
import com.frame.core.util.SystemConfig;
import com.frame.core.util.pagination.key.LimitKey;
import com.frame.core.vo.DataModel;
import com.frame.core.vo.PageParamsVo;
import com.frame.log.service.LogBizOprService;
import com.frame.system.service.UserService;
import com.util.RedisUtil;

/**
 * @ClassName: MessageServiceImpl * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Wang Xue Feng
 * @date 2015年4月28日 下午4:39:57
 * 
 */
@Service("messageService")
public class MessageServiceImpl extends PageServiceImpl<SentMessageVo> implements MessageService {

    private Log log = LogFactory.getLog(MessageServiceImpl.class);

    @Autowired
    private GenericDAO baseDAO;
    @Autowired
    private MessageLogService messageLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorService authorService;

    @Autowired
    private LogBizOprService logBizOprService;

    @Override
    public DataModel<SentMessageVo> getPageList(LimitKey key, PageParamsVo pageParamsVo) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getCountByKey(LimitKey key, PageParamsVo pageParamsVo) throws ServiceException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public SentMessageVo saveMessage(PushMessageParamVo messageVo) throws ServiceException {
        log.trace("saveOrder(" + messageVo + ") - start");
        // 初始生成消息编号
        String msgId = "MSG" + GenerateSequenceUtil.generateSequenceNo();
        String logMessage="";
        try {
           
            // 载入消息内容信息,并进行数据记录
            TMsgInfo message = new TMsgInfo();
            
            message.setMsgId(msgId);
            message.setMsgFrom("0");
            message.setMsgType(messageVo.getMsgType());
            message.setMsgContent(messageVo.getMsgContent());
            message.setMsgTitle(messageVo.getMsgTitle());
            message.setSentDelay(messageVo.getMsgSentDelay());
            if (StringUtil.notNull(messageVo.getMsgSentDelayTime())) {
                message.setSentDelayTime(DateUtil.stringToTimestamp(messageVo.getMsgSentDelayTime()));
            }
            if (StringUtil.notNull(messageVo.getMsgExpireTime())) {
                message.setOverDt(DateUtil.stringToTimestamp(messageVo.getMsgExpireTime()));
            }
            message.setSentUserGrop("1");
            message.setCreateUser(messageVo.getMsgCreator());
            message.setCreateDt(DateUtil.dateToTimestamp(new Date()));
            message.setIsValid(messageVo.getMsgIsValid());
            message.setPlatform(messageVo.getMsgPlatform());
            message.setDoAudit("0");
            message.setDoSend("0");
            message.setMsgUrlType("0");
            baseDAO.saveOrUpdate(message);
            
			logBizOprService.saveLog("通知推送管理 ","1" , "增加推送消息(Id：" + messageVo.getMsgId()+"名称：" + messageVo.getMsgContent()+ ")", "","");
            return new SentMessageVo(msgId, SentStatus.SUCCESS, 0, 0);
        } catch (Exception ex) {
        	 return new SentMessageVo(msgId, SentStatus.SUCCESS, 0, 0);
            
        }
    }


    @Override
    public List<PushMessageParamVo> queryList(MsgLimitKey key) throws ServiceException {
        List<PushMessageParamVo> messageList = new ArrayList<PushMessageParamVo>();
        // 获取SQL语句
        
        StringBuffer sqlQuery = TMessageSQL.queryDataFrom();
        if (StringUtil.isNotEmpty(key.getSearchMsgFrom())&&!"0".equals(key.getSearchMsgFrom())) {
			
        	sqlQuery.append(" and MSG_PLATFORM = '"+key.getSearchMsgFrom()+"'") ;
		}
        if (StringUtil.isNotEmpty(key.getSearchUserTel())||"0".equals(key.getSearchUserTel())) {
			
        	sqlQuery.append(" and MSG_CONTENT  like '%" + key.getSearchUserTel() + "%'");
		}
		if (StringUtil.isNotEmpty(key.getStartDt())) {
			sqlQuery.append(" and date_format(SENT_DELAY_TIME,'%Y-%m-%d %h:%i:%s') > '"+key.getStartDt()+"'") ;
		}
		if (StringUtil.isNotEmpty(key.getEndDt())) {
			
			sqlQuery.append(" and date_format(SENT_DELAY_TIME,'%Y-%m-%d %h:%i:%s') < '"+key.getEndDt()+"'") ;
			
		}
		if (StringUtil.isNotEmpty(key.getMsgType())) {
			
        	sqlQuery.append(" and MSG_TYPE = '"+key.getMsgType()+"'") ;
		}
		sqlQuery.append("  ORDER BY SENT_DELAY_TIME desc");
        // 执行SQL语句
        Query query = baseDAO.getSession().createSQLQuery(sqlQuery.toString());

        query.setFirstResult(key.getOffset() > 0 ? key.getOffset() : 0);
        query.setMaxResults(key.getPageSize() > 0 ? key.getPageSize() : 0);
        List<?> queryList = query.list();
        try {
            // 重新装载数据
            for (int i = 0; i < queryList.size(); i++) {
                PushMessageParamVo vo = new PushMessageParamVo();
                Object[] dataMap = (Object[]) queryList.get(i);
                // 绑定数据
                TMessageSQLData.bindSQLData(vo, dataMap);
                messageList.add(vo);
            }
        } catch (Exception ex) {
            // 因为在此处容易造成数据读取异常，所以加个异常来捕获，方便调试
            ex.printStackTrace();
        }
        // 返回数据结果
        return messageList;
    }

    @Override
    public int getTotalCount(MsgLimitKey key) throws ServiceException {
        // 获取SQL语句
        StringBuffer sqlQuery = TMessageSQL.queryCountFrom();
        
        if (StringUtil.isNotEmpty(key.getSearchMsgFrom())||"0".equals(key.getSearchMsgFrom())) {
			
        	sqlQuery.append(" and MSG_PLATFORM = '"+key.getSearchMsgFrom()+"'") ;
		}
        if (StringUtil.isNotEmpty(key.getSearchUserTel())||"0".equals(key.getSearchUserTel())) {
			
        	sqlQuery.append(" and MSG_CONTENT like '%" + key.getSearchUserTel() + "%'");
		}
		if (StringUtil.isNotEmpty(key.getStartDt())) {
			sqlQuery.append(" and date_format(SENT_DELAY_TIME,'%Y-%c-%d %h:%i:%s') > '"+key.getStartDt()+"'") ;
		}
		if (StringUtil.isNotEmpty(key.getEndDt())) {
			
			sqlQuery.append(" and date_format(SENT_DELAY_TIME,'%Y-%c-%d %h:%i:%s') < '"+key.getEndDt()+"'") ;
			
		}
        // 执行SQL语句
        Query query = baseDAO.getSession().createSQLQuery(sqlQuery.toString());
        List<?> queryList = query.list();
        if (queryList != null) {
            try {
                // SQL语句只查询了一个字段结果，所以可以直接取出数据
                return Integer.parseInt(queryList.get(0).toString());
            } catch (Exception ex) {
                // 因为在此处容易造成数据读取异常，所以加个异常来捕获，方便调试
                ex.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public PushMessageParamVo getMessageById(String id) throws ServiceException {
        PushMessageParamVo message = new PushMessageParamVo();
        // 获取SQL语句
        StringBuffer sqlQuery = TMessageSQL.queryDataFrom().append(" AND MSG_ID= '" + id + "'");
        // 执行SQL语句
        Query query = baseDAO.getSession().createSQLQuery(sqlQuery.toString());
        // 获取数据。ps:此处未知道到获取单条数据的方法，若有请指出修改
        List<?> queryList = query.list();
        try {
            // 重新装载数据
            if (queryList.size() != 0) {
                Object[] dataMap = (Object[]) queryList.get(0);
                // 绑定数据
                TMessageSQLData.bindSQLData(message, dataMap);
            }
        } catch (Exception ex) {
            // 因为在此处容易造成数据读取异常，所以加个异常来捕获，方便调试
            ex.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean updateMessage(PushMessageParamVo message) throws ServiceException {
    	TMsgInfo updateMessage = baseDAO.get(TMsgInfo.class, message.getMsgId());
        String logMessage = "";
        // 赋值更新数据
        updateMessage.setMsgFrom("0");
        updateMessage.setMsgType(message.getMsgType());
        updateMessage.setMsgTitle(message.getMsgTitle());
        updateMessage.setMsgContent(message.getMsgContent());
        updateMessage.setPlatform(message.getMsgPlatform());
        updateMessage.setSentUserGrop(message.getMsgSentUserGroup());
        updateMessage.setSentDelay(message.getMsgSentDelay());
        if (StringUtil.notNull(message.getMsgSentDelayTime())) {
        	updateMessage.setSentDelayTime(DateUtil.stringToTimestamp(message.getMsgSentDelayTime()));
        }
        
        updateMessage.setModifyUser(message.getMsgModifyer());
        updateMessage.setModifyDt(DateUtil.dateToTimestamp(new Date()));
        
        /**
         * 发送推送信息
         */
        boolean sendPush = false;
        if (message.getDoAudit() == 1) {
        	updateMessage.setDoAudit(message.getDoAudit()+"");
            logMessage = "审核状态变为：审核通过；";
            if (message.getMsgSentDelay().equals("0")) {
                JPushUserType userType = null;
                JPushPlatformType platform = null;

                // 设备平台判断
                if ("ANDROID".equals(message.getMsgPlatform())) {
                    platform = JPushPlatformType.ANDROID;
                } else if ("IOS".equals(message.getMsgPlatform())) {
                    platform = JPushPlatformType.IOS;
                } else {
                    platform = JPushPlatformType.ALL;
                }

                // 用户群体判断
                if (message.getMsgSentUserGroup().equals("1")) {
                    userType = JPushUserType.USER;
                }
                

                // 发送推送
                sendPush = new JPush(userType, platform).sendNotification(message.getMsgContent());
               
            }
            // 如果发送成功
            if (sendPush) {
                logMessage += "推送状态：推送成功；";
        		logBizOprService.saveLog("通知推送管理 ","2" , "审批成功，推送成功(Id：" + updateMessage.getMsgId()+"名称：" + updateMessage.getMsgContent()+ ")", "","");
        		
                updateMessage.setDoSend("1");
                updateMessage.setSentDelayTime(DateUtil.dateToTimestamp(new Date()));
               
                List<TCustom> poList= baseDAO.listAll(TCustom.class);
                for(TCustom po : poList){
                	TMsgSendLog tMsgSendLog=new TMsgSendLog();
                	if(StringUtil.isEmpty(po.getPhoneVer())){
                		tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("2");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
                	}else if(po.getPhoneVer().contains("iPhone")&&"IOS".equals(message.getMsgPlatform())){
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("2");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}else if("ANDROID".equals(message.getMsgPlatform())&&!po.getPhoneVer().contains("iPhone")){
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("2");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}else{
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("2");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}
                	
    				
    			}
                
                
            } else {
                logMessage += "推送状态：推送失败；";
        		logBizOprService.saveLog("通知推送管理 ","2" , "审批成功，推送失败(Id：" + updateMessage.getMsgId()+"名称：" + updateMessage.getMsgContent()+ ")", "","");
        		List<TCustom> poList= baseDAO.listAll(TCustom.class);
                for(TCustom po : poList){
                	TMsgSendLog tMsgSendLog=new TMsgSendLog();
    				
                	if(po.getPhoneVer().contains("iPhone")&&"IOS".equals(message.getMsgPlatform())){
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("1");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}else if("ANDROID".equals(message.getMsgPlatform())&&!po.getPhoneVer().contains("iPhone")){
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("1");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}else{
    					tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                    	tMsgSendLog.setMsgId(message.getMsgId());
                    	tMsgSendLog.setSendResult("1");
                    	tMsgSendLog.setUserId(po.getUserId());
                    	tMsgSendLog.setUserTel(po.getUserTel());
                    	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                    	tMsgSendLog.setMsgSts("0");
                    	baseDAO.save(tMsgSendLog);
    				}
    				
    			}
                updateMessage.setDoSend("-1");
                updateMessage.setSentDelayTime(DateUtil.dateToTimestamp(new Date()));
            }

        } else {
    		logBizOprService.saveLog("通知推送管理 ","2" , "审批失败(Id：" + updateMessage.getMsgId()+"名称：" + updateMessage.getMsgContent()+ ")", "","");
            updateMessage.setDoSend("0");
            updateMessage.setDoAudit("0");
        }

        baseDAO.update(updateMessage);

        // 执行更新后
        return true;
    }
    @Override
    public void changeStatus(String id, String status) {
        String sql = "update t_msg_send_log set MSG_STS='" + status + "' where LOG_ID='" + id + "'";
        Query query = baseDAO.getSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    public boolean updateAppMessage(PushMessageParamVo message) throws ServiceException {
    	TMsgInfo updateMessage = baseDAO.get(TMsgInfo.class, message.getMsgId());
        String logMessage = "";
        
        // 赋值更新数据
        updateMessage.setMsgFrom("0");
        updateMessage.setMsgType(message.getMsgType());
        updateMessage.setMsgTitle(message.getMsgTitle());
        updateMessage.setMsgContent(message.getMsgContent());
        updateMessage.setPlatform(message.getMsgPlatform());
        updateMessage.setSentUserGrop(message.getMsgSentUserGroup());
        updateMessage.setSentDelay(message.getMsgSentDelay());
        if (StringUtil.notNull(message.getMsgSentDelayTime())) {
        	updateMessage.setSentDelayTime(DateUtil.stringToTimestamp(message.getMsgSentDelayTime()));
        }
        
        updateMessage.setModifyUser(message.getMsgModifyer());
        updateMessage.setModifyDt(DateUtil.dateToTimestamp(new Date()));
        if (message.getDoAudit() == 1) {
        updateMessage.setDoSend("1");
        updateMessage.setSentDelayTime(DateUtil.dateToTimestamp(new Date()));
            // 如果发送成功
          
               if("2".equals(message.getMsgSentUserGroup())){
            	   
            	   //所有用户
            	   List<TCustom> poList= baseDAO.listAll(TCustom.class);
                   for(TCustom po : poList){
                   	TMsgSendLog tMsgSendLog=new TMsgSendLog();     				
                   	tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                   	tMsgSendLog.setMsgId(message.getMsgId());
                   	tMsgSendLog.setSendResult("2");
                   	tMsgSendLog.setUserId(po.getUserId());
                   	tMsgSendLog.setUserTel(po.getUserTel());
                   	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setMsgSts("0");
                   	baseDAO.save(tMsgSendLog);
       				
       			}
               }else if("3".equals(message.getMsgSentUserGroup())){
            	   
            	   //公寓
            	   String sql="SELECT d.`USER_ID`,d.`USER_TEL` FROM T_LAYOUT a,t_room b ,T_CUS_CHECK_IN c,t_custom d" +
            	   		" WHERE a.`LAYOUT_ID`=b.`LAYOUT_ID` AND b.`ROOM_ID`=c.`ROOM_ID` AND c.`ROOM_USER_ID`=d.`USER_ID`" +
            	   		" AND a.`PART_ID`='"+message.getPartId()+"'";
            	   Query query = baseDAO.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            	   List lsquery = query.list();
            	   if(lsquery.size()<1){
            		  
            	   }else{
            		  
            			for (int i = 0; i < lsquery.size(); i++) {
            				TMsgSendLog tMsgSendLog=new TMsgSendLog();
            				Map map = (Map) lsquery.get(i);
            				
            				tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                           	tMsgSendLog.setMsgId(message.getMsgId());
                           	tMsgSendLog.setSendResult("2");
                           	tMsgSendLog.setUserId(JSONObject.fromObject(map.get("USER_ID")).toString());
                           	tMsgSendLog.setUserTel(JSONObject.fromObject(map.get("USER_TEL")).toString());
                           	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                           	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                           	tMsgSendLog.setMsgSts("0");
                           	baseDAO.save(tMsgSendLog);
            			 }
            	   }
                   
                   	
       				
                   	
       				
       			
               }else if("4".equals(message.getMsgSentUserGroup())){
            	   //用户
            	   TCustom po= baseDAO.get(TCustom.class, message.getSentUserGropInfo());
                 
                   	TMsgSendLog tMsgSendLog=new TMsgSendLog();
       				
                   	tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                   	tMsgSendLog.setMsgId(message.getMsgId());
                   	tMsgSendLog.setSendResult("2");
                   	tMsgSendLog.setUserId(po.getUserId());
                   	tMsgSendLog.setUserTel(po.getUserTel());
                   	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setMsgSts("0");
                   	baseDAO.save(tMsgSendLog);
       				
       			
               }
               
                

        baseDAO.update(updateMessage);
        }else{

    		logBizOprService.saveLog("通知推送管理 ","2" , "审批失败(Id：" + updateMessage.getMsgId()+"名称：" + updateMessage.getMsgContent()+ ")", "","");
            updateMessage.setDoSend("0");
            updateMessage.setDoAudit("0");
        
        }
        // 执行更新后
        return true;
    }
    @Override
    public boolean saveSendJpush(String eventId,String eventInfo,List<String> aliases,List<String> redPackIdList){
	    	if(aliases==null||aliases.size()<1){
	    		return true;
	    	}
	    	log.info("msgId"+eventInfo+"aliases:"+aliases.size());
	    	  boolean flag=false;
	    	  
	    	  VEventMsgInfo po= baseDAO.get(VEventMsgInfo.class, eventId);
	    	  TMsgInfo po1=new TMsgInfo();
	    	  BeanUtils.copyProperties(po, po1);
	    	  String msgId=GUID.generateID("M");
	    	  po1.setMsgId(msgId);
	    	  po1.setCreateDt(DateUtil.dateToTimestamp(new Date()));
	    	  baseDAO.saveOrUpdate(po1);
	    	  long validHours = Long.parseLong(SystemConfig.getValue("ifpr.tokenControl.hours"));
	    	  Jedis jedis = null;
	    	 
	    		try {
	    			 jedis = RedisUtil.getJedis();
			    	for (int i = 0; i < aliases.size(); i++) {
			    		log.info("SendJpush:userId:"+aliases.get(i));
			    		List<String>  audienceAliasList=new ArrayList<String>();
			    		audienceAliasList.add(aliases.get(i));			    			   
			    				if(StringUtil.isNotEmpty(jedis.get(aliases.get(i)))){
			    					String valueStr=jedis.get(aliases.get(i));
			    					String[] valueArr=valueStr.split(";");
			    					String tokenId=valueArr[0];
			    					String loginDtStr=valueArr[1];
			    					Date loginDt=DateUtil.stringToDate(loginDtStr);
			    					long betweenHours=DateUtil.getDaysBetweenHours(loginDt, new Date());
			    					if(validHours>betweenHours){
			    						flag=true;
			    					}
			    					flag=true;
			    				}
			    				
			    		
			    		if(flag){
			    		flag=new JPush(JPushUserType.USER, JPushPlatformType.ALL).setExtra("2",redPackIdList.get(i)).setAudienceAlias(audienceAliasList).sendNotification(eventInfo);
				    	    if(flag){
					    	     for(int j=0;j<audienceAliasList.size();j++){
					    	    		TMsgSendLog tMsgSendLog=new TMsgSendLog();     				
					                   	tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
					                   	tMsgSendLog.setMsgId(msgId);
					                   	tMsgSendLog.setSendResult("2");
					                   	tMsgSendLog.setUserId(audienceAliasList.get(j));
					                   /*	TCustom tCustom=baseDAO.get(TCustom.class,aliases.get(i));
					                   	tMsgSendLog.setUserTel(tCustom.getUserTel());*/
					                   	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
					                   	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
					                   	tMsgSendLog.setMsgSts("0");					                 
					                   	String msgNumKey = "msgNum"+audienceAliasList.get(j);
					                   	if(StringUtil.isNotEmpty(jedis.get(msgNumKey))){
					    					jedis.set(msgNumKey, (Integer.parseInt(jedis.get(msgNumKey))+1)+"");
					    				}else{
					    					jedis.set(msgNumKey, 1+"");
					    				}
					                  
					                   	baseDAO.save(tMsgSendLog);
					    	     }
				    	    }
			    		}
			    	}
	    		} catch (Exception e) {
	    			

	    		} finally {
	    			try {
	    				RedisUtil.returnBrokenResource(jedis);

	    			} catch (Exception e1) {
	    				e1.printStackTrace();
	    				log.error("e1.getMessage():" + e1.getMessage());

	    			}
	    		}	
    	     return flag;
    }
    //actStatus 2开始    3结束    4审批通过    5 审批不通过
    public boolean saveSendJpush(String actStatus,String actName,List<String> aliases){
    	if(aliases==null||aliases.size()<1){
    		return true;
    	}
    	log.info("actStatus"+actStatus+"actName"+actName+"aliases:"+aliases.size());
    	for (int i = 0; i < aliases.size(); i++) {
    		log.info("SendJpush:userId:"+aliases.get(i));
    		
		}
    	
    	String msgContent="";
    	TMsgInfo updateMessage =null;
    		if("2".equals(actStatus)){
    			 updateMessage = baseDAO.get(TMsgInfo.class, "2");
    			
    			msgContent=actName+updateMessage.getMsgContent();
    		}else if("3".equals(actStatus)){
    			updateMessage = baseDAO.get(TMsgInfo.class, "3");
    			
    			
    			msgContent=actName+updateMessage.getMsgContent();
    			
    		}else if("4".equals(actStatus)){
    			updateMessage = baseDAO.get(TMsgInfo.class, "4");
    			
    			msgContent=actName+updateMessage.getMsgContent();
    		}else if ("5".equals(actStatus)){
    			updateMessage = baseDAO.get(TMsgInfo.class, "5");
    			
    			msgContent=actName+updateMessage.getMsgContent();
    		}
    		TMsgInfo tmsgInfo=new TMsgInfo();
			String msgId="MSG" + GenerateSequenceUtil.generateSequenceNo();
			tmsgInfo.setMsgId(msgId);
			tmsgInfo.setMsgTitle(updateMessage.getMsgTitle());
			tmsgInfo.setMsgType(updateMessage.getMsgType());
			
			tmsgInfo.setMsgContent(msgContent);
			tmsgInfo.setMsgFrom(updateMessage.getMsgFrom());
			tmsgInfo.setSentUserGrop(updateMessage.getSentUserGrop());
			tmsgInfo.setSentDelay(updateMessage.getSentDelay());
			tmsgInfo.setIsValid(updateMessage.getIsValid());
			tmsgInfo.setDoAudit(updateMessage.getDoAudit());
			tmsgInfo.setDoSend(updateMessage.getDoSend());
    		baseDAO.save(tmsgInfo);	
    	    boolean flag=new JPush(JPushUserType.USER, JPushPlatformType.ALL).setExtra("2").setAudienceAlias(aliases).sendNotification(msgContent);
    	    if(flag){
    	     for(int i=0;i<aliases.size();i++){
    	    		TMsgSendLog tMsgSendLog=new TMsgSendLog();     				
                   	tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                   	tMsgSendLog.setMsgId(msgId);
                   	tMsgSendLog.setSendResult("2");
                   	tMsgSendLog.setUserId(aliases.get(i));
                   	//TCustom tCustom=baseDAO.get(TCustom.class,aliases.get(i));
                   	//tMsgSendLog.setUserTel(tCustom.getUserTel());
                   	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                   	tMsgSendLog.setMsgSts("0");
                   	baseDAO.save(tMsgSendLog);
    	     }
    	    }
    	     return flag;
    }
  //向商家推送消息
    public boolean saveSendJpushShop(String title,String msgContent,List<String> aliases){
    	if(aliases==null||aliases.size()<1){
    		return true;
    	}
    	TMsgInfo updateMessage = baseDAO.get(TMsgInfo.class, "2");
    	TMsgInfo tmsgInfo=new TMsgInfo();
		String msgId="MSG" + GenerateSequenceUtil.generateSequenceNo();
		tmsgInfo.setMsgId(msgId);
		tmsgInfo.setMsgTitle(title);
		tmsgInfo.setMsgType(updateMessage.getMsgType());
		
		tmsgInfo.setMsgContent(msgContent);
		tmsgInfo.setMsgFrom(updateMessage.getMsgFrom());
		tmsgInfo.setSentUserGrop(updateMessage.getSentUserGrop());
		tmsgInfo.setSentDelay(updateMessage.getSentDelay());
		tmsgInfo.setIsValid(updateMessage.getIsValid());
		tmsgInfo.setDoAudit(updateMessage.getDoAudit());
		tmsgInfo.setDoSend(updateMessage.getDoSend());
		baseDAO.save(tmsgInfo);
    	log.info("msgContent"+msgContent+"aliases:"+aliases.size());
    	
    	    boolean flag=new JPush(JPushUserType.MERCHANT, JPushPlatformType.ALL).setAudienceAlias(aliases).sendNotification(msgContent);
    	    if(flag){
       	     for(int i=0;i<aliases.size();i++){
       	    		TMsgSendLog tMsgSendLog=new TMsgSendLog();     				
                      	tMsgSendLog.setLogId("LOG" + GenerateSequenceUtil.generateSequenceNo());
                      	tMsgSendLog.setMsgId(msgId);
                      	tMsgSendLog.setSendResult("2");
                      	tMsgSendLog.setUserId(aliases.get(i));
                      	//TCustom tCustom=baseDAO.get(TCustom.class,aliases.get(i));
                      	//tMsgSendLog.setUserTel(tCustom.getUserTel());
                      	tMsgSendLog.setSendDt(DateUtil.dateToTimestamp(new Date()));
                      	tMsgSendLog.setJobDt(DateUtil.dateToTimestamp(new Date()));
                      	tMsgSendLog.setMsgSts("0");
                      	baseDAO.save(tMsgSendLog);
       	     }
       	    }
    	     return flag;
    }
	
}
