 <#setting number_format="#"> 
<div class="wy-number">
   <div class="wy-number-xx">
        <!-- 大人信息 -->
        <ul class="wy-xx-dr">
        	<li><span class="wy-xx-mc">参赛编号 : </span><span class="wy-xx-xx">${apply.gameCode}</span></li>
            <li><span class="wy-xx-mc">选手姓名 : </span><span class="wy-xx-xx">${apply.trueName}</span></li>
            <li><span class="wy-xx-mc">手机号码 : </span><span class="wy-xx-xx">${apply.mobile}</span></li>
            <li><span class="wy-xx-mc">性别 : </span><span class="wy-xx-xx">
            <#if (apply.sex == 0)>
            	女
            <#elseif (apply.sex == 1)>
            	男
            <#else>
            	保密
            </#if>
            </span></li>
            <li><span class="wy-xx-mc">证件类型 : </span><span class="wy-xx-xx">${apply.certType}</span></li>
            <li><span class="wy-xx-mc">证件号码 : </span><span class="wy-xx-xx">${apply.certNo}</span></li>
            <li><span class="wy-xx-mc">生日 : </span><span class="wy-xx-xx">${apply.birthday}</span></li>
            <li><span class="wy-xx-mc">通讯地址 : </span><span class="wy-xx-xx">
            	${apply.country} 
            	${apply.provinceName} 
            	<#if apply.cityName??>
				 ${apply.cityName} 
				</#if>
            	${apply.address}</span></li>
            <li><span class="wy-xx-mc">电子邮箱 : </span><span class="wy-xx-xx">${apply.email}</span></li>
            <li><span class="wy-xx-mc">健康状况 : </span><span class="wy-xx-xx">${apply.healthStatus}</span></li>
            <li><span class="wy-xx-mc">有无病史 : </span><span class="wy-xx-xx">
            <#if (apply.medicalHis == 0)>
            	无
            <#else>
            	有
            </#if>
            </span></li>
            <li><span class="wy-xx-mc">血型 : </span><span class="wy-xx-xx">${apply.bloodType}</span></li>
            <li><span class="wy-xx-mc">T恤尺寸 : </span><span class="wy-xx-xx">${apply.tshirt}</span></li>
            <li><span class="wy-xx-mc">紧急联系人姓名 : </span><span class="wy-xx-xx">${apply.contactName}</span></li>
            <li><span class="wy-xx-mc">紧急联系人电话 : </span><span class="wy-xx-xx">${apply.contactTel}</span></li>
            <li><span class="wy-xx-mc">是否领物 : </span><span class="wy-xx-xx">
            <#if (apply.isGet == '1')>
            	是
            <#else>
            	否
            </#if>
            </span></li>
            <li><span class="wy-xx-mc">报名时间 : </span><span class="wy-xx-xx">${apply.addDt}</span></li>
            <li><span class="wy-xx-mc">订单信息 : </span><span class="wy-xx-xx">${apply.sportName}${apply.groupName}${apply.trueName}</span></li>
            <li><span class="wy-xx-mc">订单状态 : </span><span class="wy-xx-xx">
            <#if (apply.payStatus == 1)>
            	已支付
            <#else>
            	未支付
            </#if>
            </span></li>
            <li><span class="wy-xx-mc">订单号 : </span><span class="wy-xx-xx">${apply.tradeNo}</span></li>
            <li><span class="wy-xx-mc">支付方式 : </span><span class="wy-xx-xx">${apply.payType}</span></li>
        </ul>
        <!-- 小孩信息 -->
        <ul class="wy-xx-dr" style="border-top: 0.02rem solid #ddd">
            <li><span class="wy-xx-mc">小孩姓名 ：</span><span class="wy-xx-xx">${apply.childName}</span></li>
            <li><span class="wy-xx-mc">小孩年龄 ：</span><span class="wy-xx-xx">${apply.childAge}</span></li>
            <li><span class="wy-xx-mc">小孩性别 ：</span><span class="wy-xx-xx">
            <#if (apply.childSex == 0)>
            	女
            <#elseif (apply.childSex == 1)>
            	男
            <#else>
            	保密
            </#if>
            </span></li>
            <li><span class="wy-xx-mc">小孩证件 ：</span><span class="wy-xx-xx">${apply.childCertType}</span></li>
            <li><span class="wy-xx-mc">小孩证件号码 ：</span><span class="wy-xx-xx">${apply.childCertNo}</span></li>
            <li><span class="wy-xx-mc">小孩生日 ：</span><span class="wy-xx-xx">${(apply.childBirthDay?string("yyyy-MM-dd"))!''}</span></li>
            <li><span class="wy-xx-mc">小孩健康 ：</span><span class="wy-xx-xx">${apply.childHealthStatus}</span></li>
            <li><span class="wy-xx-mc">小孩病史 ：</span><span class="wy-xx-xx">${apply.childBloodType}</span></li>
            <li><span class="wy-xx-mc">小孩T恤 ：</span><span class="wy-xx-xx">${apply.childTshirt}</span></li>
        </ul>
    </div>
</div>