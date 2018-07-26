package easycbs.courior.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import easycbs.common.dto.BMConfig;
import easycbs.common.dto.ErrorVar;
import easycbs.common.logger.BMLogger;
import easycbs.common.parameter.LoginParameter;
import easycbs.common.util.CopyUtility;
import easycbs.common.util.LookUpClient;
import easycbs.common.web.ErrorHandler;
import easycbs.courior.actionform.BkngConsForm;
import easycbs.courior.dto.BkngConsDto;
import easycbs.courior.ejbserver.BkngConsHome;
import easycbs.courior.ejbserver.BkngConsRemote;

public class BkngConsAction extends DispatchAction {
	
	public ActionForward SaveBkngConsignment(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res) {

		HttpSession session = req.getSession(false); 
		String result="failure";
		boolean checkCons=false;
		LookUpClient LC = new LookUpClient();
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		BkngConsRemote bkngConsRemote = null;
		BkngConsForm bkngConsForm= (BkngConsForm)form;
		BkngConsDto bkngConsDto= new BkngConsDto();
		ErrorVar error=new ErrorVar();
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		LoginParameter login = (LoginParameter)session.getAttribute("LoginParameter") ; 
		String userId=String.valueOf(login.getlUserKid());
		try{
			logger.info("In BkngConsAction class");
			easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
					"BkngCons");
			bkngConsRemote = bkngConsHome.create();
			if(!login.getsBranchCode().equals(bkngConsForm.getDestinationBr())){
				checkCons=bkngConsRemote.checkConsNo(bkngConsForm.getConsNo(),error);
				if(checkCons){
					CopyUtility.copyProperties(bkngConsForm, bkngConsDto);
					reverMap=bkngConsRemote.saveBkngConsignment(bkngConsDto,error,userId);
					if(reverMap!=null)
					{		
						if(reverMap.containsKey("Errors")){
							ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
							result=(String) reverMap.get("Target");
							if(error1.size()>0){
								ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
								saveErrors(req, errors);
								if(result.equalsIgnoreCase("success"))
									bkngConsForm.reset(mapping,req);
							}
						}
					}
				}
				else{
					error.add("72075", "accNo");
					reverMap.put("Errors", error);
					if(reverMap.containsKey("Errors")){
						ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
						if(error1.size()>0){
							ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
							saveErrors(req, errors);
						}
					}
				}
			}
			else{
				error.add("1306", "accNo");
				ErrorVar error1=(ErrorVar)error;
				ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				saveErrors(req, errors);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return mapping.findForward("success");
	}
	
	public ActionForward fetchBkngConsDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res) {
		
		LookUpClient LC = new LookUpClient();
		BkngConsRemote bkngConsRemote = null;
		BkngConsForm bkngConsForm= (BkngConsForm)form;
		BkngConsDto bkngConsDto= new BkngConsDto();
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		ErrorVar error=new ErrorVar();  
		boolean checkCons=false;
		try{
			logger.info("In BkngConsAction class");
			easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
					"BkngCons");
			bkngConsRemote = bkngConsHome.create();
			String consNo=req.getParameter("ConsNo");
	
			checkCons=bkngConsRemote.checkConsNo(consNo,error);
		    if(!checkCons){
				bkngConsDto=bkngConsRemote.fetchBkngCons(consNo,error);
				if(bkngConsDto!=null)
				{
					bkngConsDto.setSearchConsNo(bkngConsForm.getSearchConsNo());
					CopyUtility.copyProperties(bkngConsDto, bkngConsForm);
					
				}
		    }   
		    else{
		    	 error.add("72074", "accNo");
		    	 ErrorVar error1=(ErrorVar)error;
				 ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				 saveErrors(req, errors);
				 bkngConsForm.reset(mapping,req);
		      }
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		return mapping.findForward("FetchSuccess");
	}
	
	public ActionForward saveBkngConsFwdDtl(ActionMapping mapping,
		ActionForm form, HttpServletRequest req, HttpServletResponse res) {
		
		HttpSession ses = req.getSession();
		LookUpClient LC = new LookUpClient();
		BkngConsRemote bkngConsRemote = null;     
		BkngConsForm bkngConsForm= (BkngConsForm)form;
		BkngConsDto bkngConsDto= new BkngConsDto();
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		ErrorVar error=new ErrorVar();  
		LoginParameter login = (LoginParameter)ses.getAttribute("LoginParameter") ; 
		try{
			logger.info("In BkngConsAction class");
			easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
					"BkngCons");
			bkngConsRemote = bkngConsHome.create();
			
			if(bkngConsForm.getFwdArrivValue().equalsIgnoreCase("fwd") && (!login.getsBranchCode().equals(bkngConsForm.getFwdbrncode()))) {
				error.add("1306", "accNo");
				ErrorVar error1=(ErrorVar)error;
				ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				saveErrors(req, errors);  
			}

			
			CopyUtility.copyProperties(bkngConsForm, bkngConsDto);   
			reverMap=bkngConsRemote.saveBkngConsFwdDtl(bkngConsDto,error,login);
				if(reverMap!=null)
				{
					CopyUtility.copyProperties(bkngConsDto, bkngConsForm);
					if(reverMap.containsKey("Errors")){
						ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
						if(error1.size()>0){
							ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
							saveErrors(req, errors);
							bkngConsForm.reset(mapping,req);
						}
					}
				}   
		    else{
		    	 error.add("72074", "accNo");
		    	 ErrorVar error1=(ErrorVar)error;
				 ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				 saveErrors(req, errors);
				 bkngConsForm.reset(mapping,req);
		      }
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		return mapping.findForward("SaveSuccess");
		}

	public ActionForward trackBkngConsDetail(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
			HttpSession session = request.getSession(false);
			LookUpClient LC = new LookUpClient();
			BkngConsRemote bkngConsRemote = null;
			BkngConsForm bkngConsForm= (BkngConsForm)form;
			BMLogger logger = new BMLogger(BkngConsAction.class); 
			ErrorVar error=new ErrorVar();  
			boolean checkCons=false;
			HashMap<String,Object> returnValue=null;
			String data[][] = null,data1 [][]=null;
			
			
			try{
				logger.info("In BkngConsAction class");
				easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
						"BkngCons");
				bkngConsRemote = bkngConsHome.create();
				String consNo=request.getParameter("ConsNo");
				
				
				
				checkCons=bkngConsRemote.checkConsNo(consNo,error);
				
					
					
			    if(!checkCons){
			    	returnValue=bkngConsRemote.trackBkngConsignment(consNo,error);
					if(returnValue!=null)
					{
						if(returnValue.containsKey("bkngData")){
							data=(String[][]) returnValue.get("bkngData");
							session.setAttribute("bkngData", data);
							if(returnValue.containsKey("trackData")){
								data1=(String[][]) returnValue.get("trackData");
								session.setAttribute("trackData", data1);
							}
						}
						return mapping.findForward("successSearch");
					}
			    }   
			    else{
			    	 error.add("72074", "accNo");
			    	 ErrorVar error1=(ErrorVar)error;
					 ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
					 saveErrors(request, errors);
					 bkngConsForm.reset(mapping,request);
			      }
			      
			}   
			
			
			catch(Exception e){
				e.printStackTrace();
			}
			
			return mapping.findForward("successSearch");
	}
		
	public ActionForward deliveryCons(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
			HttpSession session = request.getSession(false); 
			
			LookUpClient LC = new LookUpClient();
			BkngConsRemote bkngConsRemote = null;
			BkngConsForm bkngConsForm= (BkngConsForm)form;
			BMLogger logger = new BMLogger(BkngConsAction.class); 
			ErrorVar error=new ErrorVar();  
			LoginParameter login = (LoginParameter)session.getAttribute("LoginParameter") ; 

			String data[][]= null;
			try{
				logger.info("In BkngConsAction class");
				String DelType=request.getParameter("DelType");
				easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
						"BkngCons");
				bkngConsRemote = bkngConsHome.create();
					data=bkngConsRemote.deliveryCons(DelType, error, login);
					if(data!=null){
						session.setAttribute("Data", data);
						request.setAttribute("DelType", DelType);
					}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("deliverySuccess");
	
	}	
	
	public ActionForward saveDeliveryDtl(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {
		
		HttpSession ses = req.getSession();
		LookUpClient LC = new LookUpClient();
		BkngConsRemote bkngConsRemote = null; 
		BkngConsForm bkngConsForm= (BkngConsForm)form;
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		ErrorVar error=new ErrorVar();  
		LoginParameter login = (LoginParameter)ses.getAttribute("LoginParameter") ;
		String ConsNo=bkngConsForm.getConsNo();
		try{
			logger.info("In BkngConsAction class");
			easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
					"BkngCons");
			bkngConsRemote = bkngConsHome.create();
			
			reverMap=bkngConsRemote.saveDeliveryDtl(ConsNo,error,login);
				if(reverMap!=null)
				{
					if(reverMap.containsKey("Errors")){
						ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
						if(error1.size()>0){
							ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
							saveErrors(req, errors);
							bkngConsForm.reset(mapping,req);
						}
					}
				}   
		    else{
		    	 error.add("72074", "accNo");
		    	 ErrorVar error1=(ErrorVar)error;
				 ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				 saveErrors(req, errors);
				 bkngConsForm.reset(mapping,req);
		      }
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		return mapping.findForward("deliverySuccess");
		}
	
	public ActionForward recConsignement(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		
		String result="recSuccess";
		HttpSession session = request.getSession(false); 
		LookUpClient LC = new LookUpClient();
		BkngConsRemote bkngConsRemote = null;
		BkngConsForm bkngConsForm= (BkngConsForm)form;
		BkngConsDto bkngConsDto= new BkngConsDto();
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		ErrorVar error=new ErrorVar(); 
		HashMap<Object,Object> reverMap = new  HashMap<Object,Object>();
		boolean checkForDel=false;
		LoginParameter login = (LoginParameter)session.getAttribute("LoginParameter") ; 
		String userId=String.valueOf(login.getlUserKid());
		try{
			logger.info("In BkngConsAction class");
			easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
					"BkngCons");
			bkngConsRemote = bkngConsHome.create();
			String ConsNo=request.getParameter("ConsNo");
			if(ConsNo==null && !bkngConsForm.getConsNo().equals(""))
				ConsNo=bkngConsForm.getConsNo();
			
			String mode=request.getParameter("Mode");
			
			checkForDel=bkngConsRemote.checkDelivery(ConsNo,error);
			if(checkForDel){
				if(bkngConsForm.getDestination().equals("") || bkngConsForm.getDestination()==null){
					bkngConsDto=bkngConsRemote.rcvPacketsAddr(ConsNo,error);
					CopyUtility.copyProperties(bkngConsDto, bkngConsForm);
				}
				
				if(bkngConsForm.getDocTypeValue().equalsIgnoreCase("rcving") && mode.equalsIgnoreCase("save")){
					FormFile photoFile=bkngConsForm.getUploadImg();
					if(photoFile!=null && !photoFile.getFileName().equals("")){
						bkngConsDto.setFileDataArr((byte[])photoFile.getFileData()); 
						bkngConsDto.setFileDataName(photoFile.getFileName());
					}
					CopyUtility.copyProperties(bkngConsForm, bkngConsDto);
					reverMap=bkngConsRemote.saveRecConsignement(bkngConsDto,error,login);
					if(reverMap!=null)
					{		
						if(reverMap.containsKey("Errors")){
							ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
							result=(String) reverMap.get("Target"); 
							if(error1.size()>0){
								ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
								saveErrors(request, errors);
							if(result.equalsIgnoreCase("success"))
									 error.add("1122", "accNo");
						    	     error1=(ErrorVar)error;
								     errors = ErrorHandler.convertToActionErrors(error1);
								     saveErrors(request, errors);
							 		 bkngConsForm.reset(mapping,request);
					 		 }
						 }
					 }
				
				}
				else if(bkngConsForm.getDocTypeValue().equalsIgnoreCase("notrcving") && mode.equalsIgnoreCase("save")
						&& !bkngConsForm.getReason().equals("")){
					CopyUtility.copyProperties(bkngConsForm, bkngConsDto);;
					reverMap=bkngConsRemote.cancelRecCons(bkngConsDto,error,login);
					if(reverMap!=null)
					{		
						if(reverMap.containsKey("Errors")){
							ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
							result=(String) reverMap.get("Target"); 
							if(error1.size()>0){
								ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
								saveErrors(request, errors);
									if(result.equalsIgnoreCase("success"))
									bkngConsForm.reset(mapping,request);
								
							}
						}
					}
					else{
						error.add("72077", "accNo");
						reverMap.put("Errors", error);
						if(reverMap.containsKey("Errors")){
							ErrorVar error1 =(ErrorVar)reverMap.get("Errors");
							if(error1.size()>0){
								ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
								saveErrors(request, errors);
							}
						}
					}
					
				}
				else{
					if(bkngConsDto.getDestination()!=null && !bkngConsDto.getDestination().equals("")) {
						PrintWriter out = response.getWriter();
						out.print(bkngConsDto.getDestination());        
						out.flush();
						return null;
					}
				}
				
			}   
		    else{
		    	PrintWriter out = response.getWriter();
				out.print("Invalid Consignment Number");        
				out.flush();
				return null;
		      }
			
		   
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 
		return mapping.findForward("recSuccess");
	}
	
	public ActionForward CorrierReports(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
			HttpSession session = request.getSession(false); 
			HttpSession ses = request.getSession();;
			LookUpClient LC = new LookUpClient();
			BkngConsRemote bkngConsRemote = null;
			BkngConsForm bkngConsForm= (BkngConsForm)form;
			BkngConsDto bkngConsDto= new BkngConsDto();
			BMLogger logger = new BMLogger(BkngConsAction.class); 
			ErrorVar error=new ErrorVar();     
			LoginParameter login = (LoginParameter)session.getAttribute("LoginParameter") ; 
			String rptCode ="21367";   
		     
		try
		{	    
			
			String RptData[][]= null;
			try{
				logger.info("In BkngConsAction class");
				easycbs.courior.ejbserver.BkngConsHome bkngConsHome = (BkngConsHome)LC.getHome("easycbs.courior.ejbserver.BkngConsHome",
						"BkngCons");
				bkngConsRemote = bkngConsHome.create();
				CopyUtility.copyProperties(bkngConsForm, bkngConsDto);
				RptData=bkngConsRemote.RptData(rptCode,bkngConsDto,error);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(RptData!=null){
			FileWriter fileWriter=null;	
			PrintWriter printWriter=null;
			String sFileName=BMConfig.getProperty("REPORTPATH", "D:\\CommonReport\\TextFile\\");
			File file = new File(sFileName);;
			if(!file.exists())
				file.mkdirs();
			Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			sFileName=sFileName+"\\rpt"+sdf.format(cal.getTime()).replaceAll(":", "")+".txt";
		    logger.info("TEXT FILE CREATED");
		    fileWriter  = new FileWriter(sFileName);
			printWriter = new PrintWriter(fileWriter,true);
			
			GETMODULE(Integer.parseInt(rptCode),printWriter,RptData,login);
		    session.setAttribute("sFileName", sFileName);
		    DownLoadFile(mapping,form,request,response);
			fileWriter.close();
			printWriter.close();
			
			
			
			}
			else{
				 error.add("11007", "accNo");
		    	 ErrorVar error1=(ErrorVar)error;
				 ActionErrors errors = ErrorHandler.convertToActionErrors(error1);
				 saveErrors(request, errors);
				 bkngConsForm.reset(mapping,request);
			}
			
		}catch(Exception e)
		{
			logger.info("error in code DuplicateAccNameList ");
			e.printStackTrace();
		}
		return mapping.findForward("reports");	
	}
	
	public String Fill(char value,int length){
		String temp="";
		for(int i=0;i<length;i++)
			temp += value;
		return temp;
	}

	public String gf_PadL(String text,int length){
		int len=text.length();
		int diff=length-len;
		String temp="";
		if(len > length){
			return (text.substring(0,length));			
		}else{
			for(int i=0;i<diff;i++)
				temp=temp+" ";
			text=temp+text;
			return text;
		}
	}

	public String gf_PadR(String text,int length){
		int len=text.length();
		int diff=length-len;
		String temp="";
		if(len > length){
			return (text.substring(0,length));			
		}else{
			for(int i=0;i<diff;i++)
				temp=temp+" ";
			text=text+temp;
			return text;
		}
	}
	
	
	public void GETMODULE(int rptCode,PrintWriter printWriter,String RptData[][],LoginParameter login)
	{
		BMLogger logger = new BMLogger(BkngConsAction.class); 
		try{
			switch(rptCode)
			{
			case 21367:   
				desineCourierTraking(RptData,printWriter,login);
				break;
			    
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				   
				printWriter.close();
			}catch(Exception ioe)
			{
				logger.info("exception in thread");
				ioe.printStackTrace();
			}
		}
	}
	
	
	public void desineCourierTraking(String[][] RptData,PrintWriter printWriter,LoginParameter login) throws IOException
	{

        String sPrintStr="";
        String sPrintStr1="";  
		int Reportwidth=145;
		sPrintStr1   = Fill(' ',48)+  gf_PadL("Courior Tracking",48);
		printWriter.println(sPrintStr1);
		sPrintStr = Fill(' ',50)+  gf_PadL(login.getsBrnEname(),50);
		printWriter.println(sPrintStr);
		printWriter.println(Fill(' ',(int)Reportwidth));
		printWriter.println(Fill('=',(int)Reportwidth));
		String sReportHeading[] = new String[2];
		sReportHeading[0] = 
				            Fill('|',1)+  gf_PadR("SNo",5)+
				            Fill('|',1)+  gf_PadR("Branch Code",20)+
				            Fill('|',1)+  gf_PadR("ConsNo",10)+
				            Fill('|',1)+  gf_PadR("Booked From",30)+ 	
				            Fill('|',1)+  gf_PadR("Booked To",30)+
				            Fill('|',1)+  gf_PadR("Booking Date",30)+   
				            Fill('|',1)+  gf_PadR("Status",10)+
				            Fill('|',1);
		sReportHeading[1] =  Fill('=',(int)Reportwidth);	
		printWriter.println(sReportHeading[0]);
		printWriter.println(sReportHeading[1]);
		String consNo="",fromBran="",BookingCons_BookingDate="",toBran="",BookingCons_Status="",BookingCons_BrnCode="",brn_ename="rthgrth";  
		
		
		
		for( int i=0;i<RptData.length;i++)
		{	
			BookingCons_BrnCode      =  RptData[i][0];
			consNo                   =  RptData[i][1];
			fromBran                 =  RptData[i][2];
			toBran                   =  RptData[i][3];
			BookingCons_BookingDate  =  RptData[i][4];
			BookingCons_Status       =  RptData[i][7];
			
			sPrintStr                =  Fill('|',1)+  gf_PadR(String.valueOf(i+1),5)+
					Fill('|',1)+  gf_PadR(BookingCons_BrnCode,20)+	
					Fill('|',1)+  gf_PadR(consNo,10)+
					Fill('|',1)+  gf_PadR(fromBran,30)+
					Fill('|',1)+  gf_PadR(toBran,30)+ 
					Fill('|',1)+  gf_PadR(BookingCons_BookingDate,30)+
					Fill('|',1)+  gf_PadR(BookingCons_Status,10)+
					
					
					Fill('|',1);
			printWriter.println(sPrintStr);
			printWriter.println(Fill('=',(int)Reportwidth));
		}
	}
	public ActionForward DownLoadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			 HttpServletResponse response)throws IOException, ServletException { 
		 HttpSession session=request.getSession(false);
		 String sClientFile="";
		 String target = "Downloadfile";
		 ActionErrors errors=new ActionErrors();
		 FileInputStream fileInputStream = null;  
		 String sfilePath="";
		 String filename = "";
		 try 
		 {
			 sfilePath = (String)session.getAttribute("sFileName"); //C:\CKYCUPLOADFILES\Upload/IN3524_IN3524RG_25122017104104_IA005896_U91698.zip
			 if(sfilePath!=null){
				 session.removeAttribute("filePath");
				 String sDirname = sfilePath;
				 int fileInd = sDirname.lastIndexOf("\\"); 
				 sDirname=sDirname.substring(0,fileInd);
				 filename=sfilePath.substring(sDirname.length()+1);
				 sClientFile = filename;
	
	
				 OutputStream out =response.getOutputStream();
				 response.setContentType("application/zip");
				 response.setHeader("Content-Disposition","attachment; filename=" + sClientFile + "");
				 fileInputStream =new FileInputStream(new File(sfilePath));
				 Scanner file = new Scanner(new File(sfilePath));

				 int k=-1;
				 if(fileInputStream != null){
					 while ((k=fileInputStream.read()) != -1) {
						 out.write(k);       
					 } 
					 fileInputStream.close();
					 out.flush();
				 }    
				 out.close(); 
			 }
			 else{
				 errors.add("error",new ActionError("70354"));	
				 saveErrors(request,errors);
				 return mapping.findForward("downloadRept");
			 }

		 }
		 catch(SocketException se)
		 {
			 se.printStackTrace();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 errors.add("error",new ActionError("1965"));
		 saveErrors(request,errors);
		 return mapping.findForward("successEnglish");
	 }
}



