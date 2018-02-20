package com.aj.familyrelation.service;

import java.util.List;
import java.util.Map;

import com.aj.familyrelation.vo.TFamilyRelationship;
import com.aj.sys.vo.TCallEnum;
import com.frame.core.dao.GenericDAO;
import com.util.DateUtils;

public class CreateFamilyRelationshipThread extends Thread {

	private GenericDAO baseDAO;

	private String createUserId;

	private String relationUserId;

	private String callId;

	public CreateFamilyRelationshipThread(String createUserId,
			String relationId, String callId, GenericDAO baseDAO) {

		this.createUserId = createUserId;
		this.relationUserId = relationId;
		this.callId = callId;
		this.baseDAO = baseDAO;
	}

	@Override
	public void run() {
		// 根据createUserId查询所有有关系的家人，仅限3代之内
		String sql = "SELECT r.RELATION_USER_ID, r.CALL_ID, u.SEX FROM t_family_relationship r, t_user u, t_call_enum c WHERE r.CREATE_USER_ID = u.id AND r.CALL_ID = c.ID AND c.LEVEL >=-1 "
				+ "AND c.LEVEL <=1 AND  r.CREATE_USER_ID = ? AND r.IS_PRIVATE = 1 AND r.IS_DEL = 1   AND r.IS_CONFIRM = 0";
		//创建人的家人列表
		List<Map<String, Object>> createFamiltUserList = this.baseDAO
				.getGenericBySQL(sql, new Object[] { this.createUserId });
		//查询callID的level，然后根据level查询relation对应 user（比如我添加了一个爸爸，那么就查询出爸爸的同级和下一级关系）
		
		String raltionFamilyUserSql = "SELECT r.RELATION_USER_ID, r.CALL_ID, u.SEX FROM t_family_relationship r, t_user u, t_call_enum c " +
				"WHERE r.CREATE_USER_ID = u.id AND r.CALL_ID = c.ID AND c.LEVEL >=-1 AND c.LEVEL <=1 "+ 
				"AND  r.CREATE_USER_ID = ? AND r.IS_PRIVATE = 1 AND r.IS_DEL = 1   AND r.IS_CONFIRM = 0";

		//被创建家人自身已包含的其他家人
		List<Map<String, Object>> realtionFamiltUserList = this.baseDAO
				.getGenericBySQL(raltionFamilyUserSql, new Object[] { this.relationUserId });
		
		
		if (createFamiltUserList != null && createFamiltUserList.size() > 0) {
			for (int i = 0; i < createFamiltUserList.size(); i++) {
				Map<String, Object> relationMap = createFamiltUserList.get(i);
				String sex = relationMap.get("SEX").toString();
				String crrentCallId = relationMap.get("CALL_ID").toString();
				String ruId = relationMap.get("RELATION_USER_ID").toString();

				/***************** 分割线 ************************/
				
				
				
				if ("1".equals(callId)) {
					// if("0".equals(sex)){
					// //生成一个父亲和儿子的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 7);
					// }
					// }else if("1".equals(sex)){
					// //生成一个父亲和女儿的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 8);
					// }
					// }
					if ("2".equals(crrentCallId)) {
						// 母亲, 生成父亲和母亲的关系
						createRelation(ruId, relationUserId, 6);
						// 同时生成父亲和母亲的反向关系
						createRelation(relationUserId, ruId, 5);
					} else if ("3".equals(crrentCallId)
							|| "21".equals(crrentCallId)) {
						// 岳父, 生成父亲和岳父的关系或者 公公和爸爸的关系
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 29);
					} else if ("4".equals(crrentCallId)
							|| "22".equals(crrentCallId)) {
						// 岳母, 生成父亲和岳母的关系 或者婆婆和爸爸的关系
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 30);
					} else if ("5".equals(crrentCallId)) {
						// 老婆, 生成父亲和老婆的关系
						createRelation(ruId, relationUserId, 21);
						createRelation(relationUserId, ruId, 15);
					} else if ("6".equals(crrentCallId)) {
						// 老公, 生成父亲和老公的关系
						createRelation(ruId, relationUserId, 3);
						createRelation(relationUserId, ruId, 16);
					} else if ("7".equals(crrentCallId)) {
						// 儿子, 生成父亲和儿子的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 17);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 25);
						}
					} else if ("8".equals(crrentCallId)) {
						// 女儿, 生成父亲和女儿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 18);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 26);
						}
					} else if ("15".equals(crrentCallId)) {
						// 儿媳, 生成父亲和儿媳的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 19);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 27);
						}
					} else if ("16".equals(crrentCallId)) {
						// 女婿, 生成父亲和女婿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 20);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 28);
						}
					} else if ("24".equals(crrentCallId)) {
						// 后妈
						createRelation(ruId, relationUserId, 6);
						createRelation(relationUserId, ruId, 5);
					}
					
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//爸爸已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//爸爸的爸爸，爷爷
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 9);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 17);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 18);
								}
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//爸爸的妈妈，奶奶
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 10);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 17);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 18);
								}
							}
						}else if("3".equals(relationCallId)){
							//爸爸的岳父， 外公
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 11);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 25);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 26);
								}
							}
						}else if("4".equals(relationCallId)){
							//爸爸的岳母， 外婆
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 12);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 25);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 26);
								}
							}
						}else if("5".equals(relationCallId)){
							//爸爸的老婆， 妈妈
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 2);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 7);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 8);
								}
							}
						}else if("7".equals(relationCallId)){
							//爸爸的儿子， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 31);
									createRelation(currentUserId, createUserId, 31);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}
							}
						}else if("8".equals(relationCallId)){
							//爸爸的女儿， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 33);
									createRelation(currentUserId, createUserId, 33);
								}
							}
						}else if("15".equals(relationCallId)){
							//爸爸的儿媳， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 33);
									createRelation(currentUserId, createUserId, 33);
								}
							}
						}else if("16".equals(relationCallId)){
							//爸爸的女婿， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 31);
									createRelation(currentUserId, createUserId, 31);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}
							}
						}
						
					}
					
					
				} else if ("2".equals(callId)) {
					// if("0".equals(sex)){
					// //生成一个妈妈和儿子的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 7);
					// }
					// }else if("1".equals(sex)){
					// //生成一个妈妈和女儿的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 8);
					// }
					// }
					if ("1".equals(crrentCallId)) {
						// 母亲, 生成父亲和母亲的关系
						createRelation(ruId, relationUserId, 5);
						// 同时生成父亲和母亲的反向关系
						createRelation(relationUserId, ruId, 6);
					} else if ("3".equals(crrentCallId)
							|| "21".equals(crrentCallId)) {
						// 岳父, 生成父亲和岳父的关系或者 公公和爸爸的关系
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 29);
					} else if ("4".equals(crrentCallId)
							|| "22".equals(crrentCallId)) {
						// 岳母, 生成父亲和岳母的关系 或者婆婆和爸爸的关系
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 30);
					} else if ("5".equals(crrentCallId)) {
						// 老婆, 生成父亲和老婆的关系
						createRelation(ruId, relationUserId, 22);
						createRelation(relationUserId, ruId, 15);
					} else if ("6".equals(crrentCallId)) {
						// 老公, 生成父亲和老公的关系
						createRelation(ruId, relationUserId, 4);
						createRelation(relationUserId, ruId, 16);
					} else if ("7".equals(crrentCallId)) {
						// 儿子, 生成父亲和儿子的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 17);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 25);
						}
					} else if ("8".equals(crrentCallId)) {
						// 女儿, 生成父亲和女儿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 18);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 26);
						}
					} else if ("15".equals(crrentCallId)) {
						// 儿媳, 生成父亲和儿媳的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 19);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 27);
						}
					} else if ("16".equals(crrentCallId)) {
						// 女婿, 生成父亲和女婿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 20);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 28);
						}
					} else if ("23".equals(crrentCallId)) {
						// 后爸
						createRelation(ruId, relationUserId, 5);
						createRelation(relationUserId, ruId, 6);
					}
					
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//妈妈已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//妈妈的爸爸，外公
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 11);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 25);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 26);
								}
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//妈妈的妈妈，外婆
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 12);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 25);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 26);
								}
							}
						}else if("21".equals(relationCallId)){
							//妈妈的公公， 爷爷
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 9);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 17);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 18);
								}
							}
						}else if("22".equals(relationCallId)){
							//妈妈的婆婆， 奶奶
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 10);
								if("0".equals(sex)){
									//男   孙子
									createRelation(currentUserId, createUserId, 17);
								}else if("1".equals(sex)){
									//女 孙女
									createRelation(currentUserId, createUserId, 18);
								}
							}
						}else if("6".equals(relationCallId)){
							//妈妈的老公， 爸爸
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 1);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 7);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 8);
								}
							}
						}else if("7".equals(relationCallId)){
							//妈妈的儿子， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 31);
									createRelation(currentUserId, createUserId, 31);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}
							}
						}else if("8".equals(relationCallId)){
							//爸爸的女儿， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 33);
									createRelation(currentUserId, createUserId, 33);
								}
							}
						}else if("15".equals(relationCallId)){
							//爸爸的儿媳， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 33);
									createRelation(currentUserId, createUserId, 33);
								}
							}
						}else if("16".equals(relationCallId)){
							//爸爸的女婿， 兄弟姐妹
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 31);
									createRelation(currentUserId, createUserId, 31);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 32);
									createRelation(currentUserId, createUserId, 32);
								}
							}
						}
					}
					
				} else if ("3".equals(callId)) {
					//理论说是不能有女婿添加岳父岳母的
					// createRelation(relationUserId, createUserId, 16);

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						// 岳父和父亲关系
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 29);
					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						// 岳父和妈妈
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 30);
					} else if ("4".equals(crrentCallId)) {
						// 岳母,
						createRelation(ruId, relationUserId, 6);
						createRelation(relationUserId, ruId, 5);
					} else if ("5".equals(crrentCallId)) {
						// 老婆,
						createRelation(ruId, relationUserId, 1);
						createRelation(relationUserId, ruId, 8);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 11);
						createRelation(relationUserId, ruId, 25);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 11);
						createRelation(relationUserId, ruId, 26);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳
						createRelation(ruId, relationUserId, 11);
						createRelation(relationUserId, ruId, 27);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 11);
						createRelation(relationUserId, ruId, 28);
					}
				} else if ("4".equals(callId)) {
					// 岳母  //理论说是不能有女婿添加岳父岳母的
					// createRelation(relationUserId, createUserId, 16);

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						// 岳母和父亲关系
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 29);
					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						// 岳父和妈妈
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 30);
					} else if ("3".equals(crrentCallId)) {
						// 岳母,
						createRelation(ruId, relationUserId, 5);
						createRelation(relationUserId, ruId, 6);
					} else if ("5".equals(crrentCallId)) {
						// 老婆,
						createRelation(ruId, relationUserId, 2);
						createRelation(relationUserId, ruId, 8);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 12);
						createRelation(relationUserId, ruId, 25);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 12);
						createRelation(relationUserId, ruId, 26);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳
						createRelation(ruId, relationUserId, 12);
						createRelation(relationUserId, ruId, 27);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 12);
						createRelation(relationUserId, ruId, 28);
					}
				} else if ("5".equals(callId)) {
					// 老婆
					// createRelation(relationUserId, createUserId, 6);

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						createRelation(ruId, relationUserId, 15);
						createRelation(relationUserId, ruId, 21);
					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						createRelation(ruId, relationUserId, 15);
						createRelation(relationUserId, ruId, 22);
					} else if ("3".equals(crrentCallId)) {
						// 岳父,
						createRelation(ruId, relationUserId, 8);
						createRelation(relationUserId, ruId, 1);
					} else if ("4".equals(crrentCallId)) {
						// 岳母
						createRelation(ruId, relationUserId, 8);
						createRelation(relationUserId, ruId, 2);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 2);
						createRelation(relationUserId, ruId, 7);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 2);
						createRelation(relationUserId, ruId, 8);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳
						createRelation(ruId, relationUserId, 22);
						createRelation(relationUserId, ruId, 15);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 4);
						createRelation(relationUserId, ruId, 16);
					}
					
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//老婆已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//老婆的爸爸，岳父
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 3);
								createRelation(currentUserId, createUserId, 16);
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//老婆的妈妈，岳母
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 4);
								createRelation(currentUserId, createUserId, 16);
							}
						}
						//理论来说老婆在添加老公之前，是没有途径添加儿女的
						/*else if("7".equals(relationCallId)){
							//老婆的儿子， 儿子
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 7);
								createRelation(currentUserId, createUserId, 1);
							}
						}else if("8".equals(relationCallId)){
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 8);
								createRelation(currentUserId, createUserId, 1);
							}
						}*/
						
					}
					
					
					
					
				} else if ("6".equals(callId)) {
					// 老公
					// createRelation(relationUserId, createUserId, 5);

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						createRelation(ruId, relationUserId, 16);
						createRelation(relationUserId, ruId, 3);
					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						createRelation(ruId, relationUserId, 16);
						createRelation(relationUserId, ruId, 4);
					} else if ("21".equals(crrentCallId)) {
						// 公公,
						createRelation(ruId, relationUserId, 7);
						createRelation(relationUserId, ruId, 1);
					} else if ("22".equals(crrentCallId)) {
						// 婆婆
						createRelation(ruId, relationUserId, 7);
						createRelation(relationUserId, ruId, 2);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 1);
						createRelation(relationUserId, ruId, 7);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 1);
						createRelation(relationUserId, ruId, 8);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳
						createRelation(ruId, relationUserId, 21);
						createRelation(relationUserId, ruId, 15);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 3);
						createRelation(relationUserId, ruId, 16);
					}
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//老公已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//老公的爸爸，公公
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 21);
								createRelation(currentUserId, createUserId, 15);
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//老公的妈妈，婆婆
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 22);
								createRelation(currentUserId, createUserId, 15);
							}
						}
						//理论来说老公在添加老婆之前，是没有途径添加儿女的
						
					}
					
					
				} else if ("7".equals(callId)) {
					// 儿子
					// if("0".equals(sex)){
					// createRelation(relationUserId, createUserId, 1);
					// }else if("1".equals(sex)){
					// createRelation(relationUserId, createUserId, 2);
					// }

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 17);
							createRelation(relationUserId, ruId, 9);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 25);
							createRelation(relationUserId, ruId, 11);
						}

					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 17);
							createRelation(relationUserId, ruId, 10);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 25);
							createRelation(relationUserId, ruId, 12);
						}
					} else if ("3".equals(crrentCallId)) {
						// 岳父
						createRelation(ruId, relationUserId, 25);
						createRelation(relationUserId, ruId, 11);
					} else if ("4".equals(crrentCallId)) {
						// 公公,
						createRelation(ruId, relationUserId, 25);
						createRelation(relationUserId, ruId, 12);
					} else if ("21".equals(crrentCallId)) {
						// 公公,
						createRelation(ruId, relationUserId, 17);
						createRelation(relationUserId, ruId, 9);
					} else if ("22".equals(crrentCallId)) {
						// 婆婆
						createRelation(ruId, relationUserId, 17);
						createRelation(relationUserId, ruId, 10);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 31);
						createRelation(relationUserId, ruId, 31);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 32);
						createRelation(relationUserId, ruId, 32);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳（由于我们不能直接添加儿媳女婿，所以这里的儿媳女婿一定是其他儿子或女儿的对象，所以这里生成兄弟姐妹关系）
						createRelation(ruId, relationUserId, 32);
						createRelation(relationUserId, ruId, 32);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 31);
						createRelation(relationUserId, ruId, 31);
					}
					
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//儿子已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//儿子的爸爸，分2种情况， 我的性别是男，就不添加， 我的性别是女， 则是老公-老婆关系
							if("1".equals(sex)){
								
								if(isNotExist(createUserId, currentUserId)){
									createRelation(createUserId, currentUserId, 6);
									createRelation(currentUserId, createUserId, 5);
								}
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//儿子的妈妈， 同上
							if("0".equals(sex)){
								if(isNotExist(createUserId, currentUserId)){
									createRelation(createUserId, currentUserId, 5);
									createRelation(currentUserId, createUserId, 6);
								}
							}
						}else if("3".equals(relationCallId)){
							//儿子的岳父， 亲家
							
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 29);
									createRelation(currentUserId, createUserId, 29);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 29);
									createRelation(currentUserId, createUserId, 30);
								}
							}
						}else if("4".equals(relationCallId)){
							//儿子的岳母， 亲家母
							
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 30);
									createRelation(currentUserId, createUserId, 29);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 30);
									createRelation(currentUserId, createUserId, 30);
								}
							}
						}else if("5".equals(relationCallId)){
							//儿子的老婆，儿媳
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 15);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 21);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 22);
								}
							}
						}else if("7".equals(relationCallId)){
							//儿子的儿子， 孙子
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 17);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 9);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 10);
								}
							}
						}else if("8".equals(relationCallId)){
							//儿子的女儿， 孙女
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 18);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 9);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 10);
								}
							}
						}else if("15".equals(relationCallId)){
							//儿子的儿媳， 孙儿媳妇
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 19);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 9);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 10);
								}
							}
						}else if("16".equals(relationCallId)){
							//儿子的女婿， 孙女婿
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 20);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 9);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 10);
								}
							}
						}
					}
				} else if ("8".equals(callId)) {
					// 女儿
					// if("0".equals(sex)){
					// createRelation(relationUserId, createUserId, 1);
					// }else if("1".equals(sex)){
					// createRelation(relationUserId, createUserId, 2);
					// }

					if ("1".equals(crrentCallId) || "23".equals(crrentCallId)) {
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 18);
							createRelation(relationUserId, ruId, 9);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 26);
							createRelation(relationUserId, ruId, 11);
						}

					} else if ("2".equals(crrentCallId)
							|| "24".equals(crrentCallId)) {
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 18);
							createRelation(relationUserId, ruId, 10);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 26);
							createRelation(relationUserId, ruId, 12);
						}
					} else if ("3".equals(crrentCallId)) {
						// 岳父
						createRelation(ruId, relationUserId, 26);
						createRelation(relationUserId, ruId, 11);
					} else if ("4".equals(crrentCallId)) {
						// 公公,
						createRelation(ruId, relationUserId, 26);
						createRelation(relationUserId, ruId, 12);
					} else if ("21".equals(crrentCallId)) {
						// 公公,
						createRelation(ruId, relationUserId, 18);
						createRelation(relationUserId, ruId, 9);
					} else if ("22".equals(crrentCallId)) {
						// 婆婆
						createRelation(ruId, relationUserId, 18);
						createRelation(relationUserId, ruId, 10);
					} else if ("7".equals(crrentCallId)) {
						// 儿子,
						createRelation(ruId, relationUserId, 32);
						createRelation(relationUserId, ruId, 32);
					} else if ("8".equals(crrentCallId)) {
						// 女儿,
						createRelation(ruId, relationUserId, 33);
						createRelation(relationUserId, ruId, 33);
					} else if ("15".equals(crrentCallId)) {
						// 儿媳（由于我们不能直接添加儿媳女婿，所以这里的儿媳女婿一定是其他儿子或女儿的对象，所以这里生成兄弟姐妹关系）
						createRelation(ruId, relationUserId, 33);
						createRelation(relationUserId, ruId, 33);
					} else if ("16".equals(crrentCallId)) {
						// 女婿,
						createRelation(ruId, relationUserId, 32);
						createRelation(relationUserId, ruId, 32);
					}
					
					
					for(int j=0; j<realtionFamiltUserList.size(); j++ ){
						//女儿已经添加的家人
						Map<String, Object> realtionFamiltUserMap = realtionFamiltUserList.get(j);
						String relationCallId =  realtionFamiltUserMap.get("CALL_ID").toString();
						String currentUserId =  realtionFamiltUserMap.get("RELATION_USER_ID").toString();
						if("1".equals(relationCallId) || "23".equals(relationCallId)){
							//女儿的爸爸，分2种情况， 我的性别是男，就不添加， 我的性别是女， 则是老公-老婆关系
							if("1".equals(sex)){
								
								if(isNotExist(createUserId, currentUserId)){
									createRelation(createUserId, currentUserId, 6);
									createRelation(currentUserId, createUserId, 5);
								}
							}
							
						}else if("2".equals(relationCallId) || "24".equals(relationCallId)){
							//女儿的妈妈， 同上
							if("0".equals(sex)){
								if(isNotExist(createUserId, currentUserId)){
									createRelation(createUserId, currentUserId, 5);
									createRelation(currentUserId, createUserId, 6);
								}
							}
						}else if("21".equals(relationCallId)){
							//女儿的公公， 亲家
							
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 29);
									createRelation(currentUserId, createUserId, 29);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 29);
									createRelation(currentUserId, createUserId, 30);
								}
							}
						}else if("22".equals(relationCallId)){
							//女儿的婆婆， 亲家母
							
							if(isNotExist(createUserId, currentUserId)){
								if("0".equals(sex)){
									//男   
									createRelation(createUserId, currentUserId, 30);
									createRelation(currentUserId, createUserId, 29);
								}else if("1".equals(sex)){
									//女 
									createRelation(createUserId, currentUserId, 30);
									createRelation(currentUserId, createUserId, 30);
								}
							}
						}else if("6".equals(relationCallId)){
							//女儿的老公 ， 女婿
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 16);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 3);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 4);
								}
							}
						}else if("7".equals(relationCallId)){
							//女儿的儿子， 外孙子
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 25);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 11);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 12);
								}
							}
						}else if("8".equals(relationCallId)){
							//女儿的女儿， 外孙女
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 26);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 11);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 12);
								}
							}
						}else if("15".equals(relationCallId)){
							//女儿的儿媳， 外孙儿媳妇
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 27);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 11);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 12);
								}
							}
						}else if("16".equals(relationCallId)){
							//女儿的女婿， 外孙女婿
							if(isNotExist(createUserId, currentUserId)){
								createRelation(createUserId, currentUserId, 28);
								if("0".equals(sex)){
									//男   
									createRelation(currentUserId, createUserId, 11);
								}else if("1".equals(sex)){
									//女 
									createRelation(currentUserId, createUserId, 12);
								}
							}
						}
					}
					
					
					
				}
				if ("23".equals(callId)) {
					// 后爸
					// if("0".equals(sex)){
					// //生成一个父亲和儿子的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 7);
					// }
					// }else if("1".equals(sex)){
					// //生成一个父亲和女儿的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 8);
					// }
					// }
					if ("2".equals(crrentCallId)) {

						if (isNotExist(ruId, relationUserId)) {
							createRelation(ruId, relationUserId, 6);
							createRelation(relationUserId, ruId, 5);
						}
					} else if ("3".equals(crrentCallId)
							|| "21".equals(crrentCallId)) {
						// 岳父, 生成父亲和岳父的关系或者 公公和爸爸的关系
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 29);
					} else if ("4".equals(crrentCallId)
							|| "22".equals(crrentCallId)) {
						// 岳母, 生成父亲和岳母的关系 或者婆婆和爸爸的关系
						createRelation(ruId, relationUserId, 29);
						createRelation(relationUserId, ruId, 30);
					} else if ("5".equals(crrentCallId)) {
						// 老婆, 生成父亲和老婆的关系
						createRelation(ruId, relationUserId, 21);
						createRelation(relationUserId, ruId, 15);
					} else if ("6".equals(crrentCallId)) {
						// 老公, 生成父亲和老公的关系
						createRelation(ruId, relationUserId, 3);
						createRelation(relationUserId, ruId, 16);
					} else if ("7".equals(crrentCallId)) {
						// 儿子, 生成父亲和儿子的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 17);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 25);
						}
					} else if ("8".equals(crrentCallId)) {
						// 女儿, 生成父亲和女儿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 18);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 26);
						}
					} else if ("15".equals(crrentCallId)) {
						// 儿媳, 生成父亲和儿媳的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 19);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 27);
						}
					} else if ("16".equals(crrentCallId)) {
						// 女婿, 生成父亲和女婿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 9);
							createRelation(relationUserId, ruId, 20);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 11);
							createRelation(relationUserId, ruId, 28);
						}
					} else if ("24".equals(crrentCallId)) {
						// 后妈
						if (isNotExist(ruId, relationUserId)) {
							createRelation(ruId, relationUserId, 6);
							createRelation(relationUserId, ruId, 5);
						}
					}
				} else if ("24".equals(callId)) {
					// if("0".equals(sex)){
					// //生成一个妈妈和儿子的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 7);
					// }
					// }else if("1".equals(sex)){
					// //生成一个妈妈和女儿的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 8);
					// }
					// }
					if ("1".equals(crrentCallId)) {
						// fu亲, 生成父亲和母亲的关系
						if (isNotExist(ruId, relationUserId)) {
							createRelation(ruId, relationUserId, 5);
							// 同时生成父亲和母亲的反向关系
							createRelation(relationUserId, ruId, 6);
						}
					} else if ("3".equals(crrentCallId)
							|| "21".equals(crrentCallId)) {
						// 岳父, 生成父亲和岳父的关系或者 公公和爸爸的关系
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 29);
					} else if ("4".equals(crrentCallId)
							|| "22".equals(crrentCallId)) {
						// 岳母, 生成父亲和岳母的关系 或者婆婆和爸爸的关系
						createRelation(ruId, relationUserId, 30);
						createRelation(relationUserId, ruId, 30);
					} else if ("5".equals(crrentCallId)) {
						// 老婆, 生成父亲和老婆的关系
						createRelation(ruId, relationUserId, 22);
						createRelation(relationUserId, ruId, 15);
					} else if ("6".equals(crrentCallId)) {
						// 老公, 生成父亲和老公的关系
						createRelation(ruId, relationUserId, 4);
						createRelation(relationUserId, ruId, 16);
					} else if ("7".equals(crrentCallId)) {
						// 儿子, 生成父亲和儿子的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 17);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 25);
						}
					} else if ("8".equals(crrentCallId)) {
						// 女儿, 生成父亲和女儿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 18);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 26);
						}
					} else if ("15".equals(crrentCallId)) {
						// 儿媳, 生成父亲和儿媳的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 19);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 27);
						}
					} else if ("16".equals(crrentCallId)) {
						// 女婿, 生成父亲和女婿的关系
						if ("0".equals(sex)) {
							createRelation(ruId, relationUserId, 10);
							createRelation(relationUserId, ruId, 20);
						} else if ("1".equals(sex)) {
							createRelation(ruId, relationUserId, 12);
							createRelation(relationUserId, ruId, 28);
						}
					} else if ("23".equals(crrentCallId)) {
						// 后爸
						if (isNotExist(ruId, relationUserId)) {

							createRelation(ruId, relationUserId, 5);
							createRelation(relationUserId, ruId, 6);
						}
					}
				}else if ("13".equals(callId)) {
					// if("0".equals(sex)){
					// //生成一个妈妈和儿子的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 7);
					// }
					// }else if("1".equals(sex)){
					// //生成一个妈妈和女儿的关系
					// if(isNotExist(createUserId, relationUserId)){
					// createRelation(relationUserId, createUserId, 8);
					// }
					// }
					if ("5".equals(crrentCallId)) {
						// 老婆, 生成老婆和胎儿的关系
						if (isNotExist(ruId, relationUserId)) {
							createRelation(ruId, relationUserId, 13);
							// 同时生成老婆和胎儿的反向关系
							createRelation(relationUserId, ruId, 2);
						}
					} else if ("6".equals(crrentCallId)) {
						// 岳父, 生成父亲和岳父的关系或者 公公和爸爸的关系
						createRelation(ruId, relationUserId, 13);
						createRelation(relationUserId, ruId, 1);
					} 
				}

				
			}
		}
	}

	private boolean isNotExist(String createUserId, String relationUserId) {

		String hsql = "from TFamilyRelationship where createUserId = ?  and relationUserId = ? and isConfirm = 0";
		List<TFamilyRelationship> list = baseDAO.getGenericByHql(hsql,
				Integer.parseInt(createUserId),
				Integer.parseInt(relationUserId));
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	private boolean isNotExist(TFamilyRelationship relation) {

		String hsql = "from TFamilyRelationship where createUserId = ?  and relationUserId = ? and isConfirm = 0";
		List<TFamilyRelationship> list = baseDAO.getGenericByHql(hsql,
				relation.getCreateUserId(), relation.getRelationUserId());
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	private void createRelation(String createUserId, String raltionUserId,
			int callId) {
		TFamilyRelationship relation = new TFamilyRelationship();
		relation.setCreateUserId(Integer.parseInt(createUserId));
		relation.setRelationUserId(Integer.parseInt(raltionUserId));
		relation.setCallId(callId);
		relation.setCreateDate(DateUtils.currentDate());
		relation.setIsConfirm(0);
		relation.setIsDel(1);
		relation.setIsPrivate(1);
		baseDAO.save(relation);
	}

}
