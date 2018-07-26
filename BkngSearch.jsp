<%@ include file = "/common/Include.jsp"  %> 
<%@ page import="easycbs.courior.actionform.BkngConsForm "%>
<% String sMode="Opr";
String data[][] = null,data1 [][]=null;
	if(session.getAttribute("bkngData")!=null){
		data = (String[][])session.getAttribute("bkngData");
		session.removeAttribute("bkngData");
	}
	if(session.getAttribute("trackData")!=null){
		data1 = (String[][])session.getAttribute("trackData");
		session.removeAttribute("trackData");
	}
%>

<html>
	<head>

		<script>
				
				function searchCons(){
					var consNo=document.getElementById("bkngSearch").value;
				if(trim(consNo)=="")
				 {
				 alert('Please enter Consignment Number');
				 document.getElementById("bkngSearch").focus;
				 return false;
				 }
				  else {
					 
					  document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=trackBkngConsDetail&ConsNo="+consNo; ;
					  document.forms[0].method="post";
					  document.forms[0].submit();
						
					 } 
					 		
				}

		function focus(){
			
			document.getElementById("bkngSearch").focus;
		}
		
				
				function gotomenu()
			{
				document.forms[0].action = appcontext +"/menu/FormOpen.do?from=cancel";
				document.forms[0].method="post";
				document.forms[0].submit();
			}

			
				

		</script>
		
	</head>
	<body onload="focus();">
		<div align="center">
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				Track Consignment <br><br>
			</label>
		</div>
		
		<html:form action="/Courior/BkngConsAction" >
	
       <table width="40%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
		 <td align="center"><b>Consignment Number</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		:<html:text styleId="bkngSearch" property="bkngSearch"   size="20" maxlength="100"  /></td>
		<td><input type="button" name="Search" id="cancel" value="<bean:message bundle='<%=lang%>' key='11690'/>" style="width:100px"  onclick="searchCons();" ></td>
		</tr>
		
	</table>
		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		
		<br>
		<br>
		<br>

		<table width="70%" border="2" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<th>Consignment Number <font size="2" color='<%=errorColor%>'></font></th>
			<th >Packet Source<font size="2" color='<%=errorColor%>'></font></th>
			<th>Packet Destination Branch<font size="2" color='<%=errorColor%>'></font></th>
			<th>Destination Address<font size="2" color='<%=errorColor%>'></font></th>
			<th >Packet Type Doc/Non-Doc<font size="2" color='<%=errorColor%>'></font> </th>
			<th>Booking Date <font size="2" color='<%=errorColor%>'></font> </th>

			

		</tr>
		<tr>
		<%if(data!=null){%>
			<td align="center"><%=data[0][0]%></td>
		    <td align="center"><%=data[0][1]%></td>
		    <td align="center"><%=data[0][2]%></td>
		    <td align="center"><%=data[0][3]%></td>
		    <td align="center"><%=data[0][4]%></td>
			<td align="center"><%=data[0][5]%></td>
<%}%>
		</tr>
		</table>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		<br>
		
		
		<table width="50%" border="2" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<th><label>Description <font size="2" color='<%=errorColor%>'></font></label> </th>
			<th ><label>Date<font size="2" color='<%=errorColor%>'></font></label> </th>
		</tr>

		<%if(data1!=null){%>
			<% for(int i=0; i<data1.length; i++){ %>
		<tr>
		
			<td align="center"><%=data1[i][0]%></td>
			<td align="center"><%=data1[i][1]%></td>
		</tr> 
			<%}%>
			<%}else{%>
				<tr><td colspan="2">No data available</td></tr>
			<%}%>

		</table>
 
		

		<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<br><br><br><br><br><br><br><br><br><br><br><br>
		<table width="70%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
		<td  align="center" colspan="2">
		<input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='87'/>" style="width:100px" onclick="gotomenu()" >
		<input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='33'/>" style="width:100px"  tabindex='6'>
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