<%@ include file = "/common/Include.jsp"  %>  
<%@ page import="easycbs.courior.actionform.BkngConsForm "%>
<% String sMode="Opr";
String param=request.getParameter("param");
%>
<html>
	<head>
	<script >
			 var gCalDate=changeDate('<%= bancDate.toString() %>');
			 var gdate=changeDateFormat('<%= bancDate.toString() %>');
		
			function showhide(di)
			{	
				var divs = di;
				if(divs == "letterDateNew")
				{
					var sty = cal_fromDate_display.style.display;
					if(sty == "none"){
						cal_fromDate_display.style.display = "block";
						cal_fromDate_display_year.style.display = "block";	
					}
				else{
					cal_fromDate_display.style.display = "none";
					cal_fromDate_display_year.style.display = "none";
					}
				}
				else if(divs == "letterDateNew1")
				{
					var sty = cal_toDate_display.style.display;
					if(sty == "none"){
						cal_toDate_display.style.display = "block";
						cal_toDate_display_year.style.display = "block";	
					}
				else{
					cal_toDate_display.style.display = "none";
					cal_toDate_display_year.style.display = "none";
					}
				}
			}
			 </script>

		 <script>
			
		
			function gotomenu()
			{
				document.forms[0].action = appcontext +"/menu/FormOpen.do?from=cancel";
				document.forms[0].method="post";
				document.forms[0].submit();
			}

			function saveConsDetail()
			{
				if(document.getElementById("brnCode").value==""){
                    alert('Please Enter Branch Code');
				 document.getElementById("brnCode").focus;
				 return false;
				}
				if(document.getElementById("fromDate").value==""){
                    alert('Please Enter Date');
				 document.getElementById("fromDate").focus;
				 return false;
				}
				if(document.getElementById("toDate").value==""){
					alert('Please Enter Date');
				 document.getElementById("toDate").focus;

				}
				else
				{

				  document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=CorrierReports&param="+'<%=param%>';	
				  document.forms[0].method="post";
				  document.forms[0].submit();

					 
				}
            }
			
         function downloadReport()
		     	{
					
					document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=DownLoadFile";
                 // document.forms[0].action=appcontext +"/servlet/easycbs.reports.servlet.DownloadServlet";	
				  document.forms[0].method="post";
				  document.forms[0].submit();
				}


		</script>
	</head>
	<body >
		<div align="center">
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				Reports<br><br>
			</label>
					
		</div>



		<html:form action="/Courior/BkngConsAction" >
		<HR>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" style="width:450px; margin:0 auto;">
		<tr>
			<td align="left" id="fbrn" style="display: block;"><label >Branch Code<font size="2" color='<%=errorColor%>'>*</font></label></td>
			<td><B>:&nbsp;</B><html:text  property="brnCode" styleId="brnCode" tabindex="90" size="10"  maxlength="10" onkeypress="alphaNumWithoutSpace()"  onkeydown="callMasterHelp('destinationBr')"/>
			<img src="<%=request.getContextPath()%>/images/HELP1.BMP"   class="Image" onClick="openHelpPanel('brnCode', 'destinationBrKid', 'destinationBrLabel','CBSBranchMaster')"> 
			 <html:text property="destinationBrLabel" styleId="destinationBrLabel" readonly="true"  size="30" style ="background-color:activeborder;border:0px" tabindex="-1"/>
			</td>
		</tr>

 
		<tr>
		<td>From Date</td>
		 <td><b>&nbsp;:</b>
		 <html:text  property="fromDate" styleId="fromDate" size="12" readonly="true" tabindex=""/>
			<!-- Calender -->			 
			<input type="button"  name="letterDateButton" onClick="showhide('letterDateNew')"  tabindex="4"
						style="background-image: url('<%=request.getContextPath()%>/images/Calendar.gif');width:20px;height:15px"
						/>			
			<div id="cal_fromDate_display_year" 
					style="display:none;position:absolute;top:100px;left:650px;">
					<input  type="text"	id="fromDate_year"size="5"maxlength="4"/>
			</div>	
			<%
			if (sMode.equalsIgnoreCase("Auth")) {%>
			
			<%}else {%>	
			<div id="cal_fromDate_display"
					 style="display:none;position:absolute;top:100px;left:950px;">				
					 <script
					 type="text/javascript">						
						letterDateNew = new Calendar ("letterDateNew","fromDate",new Date(gCalDate));
						renderCalendar (letterDateNew);						
					</script>
			</div>
			<%}%>		
		 </td>	
		</tr>

		<tr>
		<td>To Date</td>
		 <td><b>&nbsp;:</b>
		 <html:text  property="toDate" styleId="toDate" size="12" readonly="true" tabindex=""/>
			<!-- Calender -->			 
			<input type="button"  name="letterDateButton" onClick="showhide('letterDateNew1')"  tabindex="4"
						style="background-image: url('<%=request.getContextPath()%>/images/Calendar.gif');width:20px;height:15px"
						/>			
			<div id="cal_toDate_display_year" 
					style="display:none;position:absolute;top:100px;left:650px;">
					<input  type="text"	id="toDate_year"size="5"maxlength="4"/>
			</div>	
			<%
			if (sMode.equalsIgnoreCase("Auth")) {%>
			
			<%}else {%>	
			<div id="cal_toDate_display"
					 style="display:none;position:absolute;top:100px;left:950px;">				
					 <script
					 type="text/javascript">						
						letterDateNew1 = new Calendar ("letterDateNew1","toDate",new Date(gCalDate));
						renderCalendar (letterDateNew1);						
					</script>
			</div>
			<%}%>		
		 </td>	
		</tr>
	    </table>
		<table width="100%" align="center" >
		<tr>
		<td  align="center" colspan="2">
		<HR>
		<input type="button" name="saveButton" id="saveButton"  value="<bean:message bundle='<%=lang%>' key='12372'/>"  onclick ="saveConsDetail()" tabIndex='4'>
		<input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='87'/>"  onclick="gotomenu()" tabindex='5'>
		
		</td>
		</tr>
		</table>

		<table align="center">
		<DIV id = "Div" style = "display:block;">
		<script>
				var ind = 0;
				<html:messages id = "Errors" property = "ccNo" bundle='<%=errorLang%>'>
					errorArrayValue[ind] = "accNo";
					errorArrayText[ind] = '<bean:write name = "Errors" />';
					ind = ind + 1;
				</html:messages>
				showErrorTab();
		</script>
		</DIV>
		</table>
	</html:form>
	</body>
</html>

 