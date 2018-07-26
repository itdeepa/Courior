package easycbs.courior.actionform;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BkngConsForm extends ActionForm{

	private static final long serialVersionUID = 1L;
	
	private String brnKid="" ;
	private String brnCode=""; 
	private String brnLabel="";
	private String consDate="";
	private String consNo="" ; 
	private String destinationBr="" ;
	private String destinationBrKid="";
	private String destinationBrLabel="";
	private String destination ="" ; 
	private String weight="" ; 
	private String dimension="";
	private boolean docType=false;
	private String docTypeValue="";
	private boolean fwdArriv=false;
	private  String fwdArrivValue="";
	private String fwdbrncode="";
	private String bkngSearch="";
	private boolean delPacket=false;
	private String reason="";
	private FormFile uploadImg;
	public String fromDate ="";
	public String toDate ="";
	public String showDestbrn="";
	private String searchConsNo="";
	
	public String getSearchConsNo() {
		return searchConsNo;
	}
	public void setSearchConsNo(String searchConsNo) {
		this.searchConsNo = searchConsNo;
	}
	public String getShowDestbrn() {
		return showDestbrn;
	}
	public void setShowDestbrn(String showDestbrn) {
		this.showDestbrn = showDestbrn;
	}
public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public FormFile getUploadImg() {
		return uploadImg;
	}
	public void setUploadImg(FormFile uploadImg) {
		this.uploadImg = uploadImg;
	}
	public boolean isDelPacket() {
		return delPacket;
	}
	public void setDelPacket(boolean delPacket) {
		this.delPacket = delPacket;
	}

	public boolean isFwdArriv() {
		return fwdArriv;
	}
	public void setFwdArriv(boolean fwdArriv) {
		this.fwdArriv = fwdArriv;
	}
	public String getFwdArrivValue() {
		return fwdArrivValue;
	}
	public void setFwdArrivValue(String fwdArrivValue) {
		this.fwdArrivValue = fwdArrivValue;
	}
	public String getFwdbrncode() {
		return fwdbrncode;
	}
	public void setFwdbrncode(String fwdbrncode) {
		this.fwdbrncode = fwdbrncode;
	}
	public String getBkngSearch() {
		return bkngSearch;
	}
	public void setBkngSearch(String bkngSearch) {
		this.bkngSearch = bkngSearch;
	}
	public String getDocTypeValue() {
		return docTypeValue;
	}
	public void setDocTypeValue(String docTypeValue) {
		this.docTypeValue = docTypeValue;
	}

	public String getBrnKid() {
		return brnKid;
	}
	public void setBrnKid(String brnKid) {
		this.brnKid = brnKid;
	}
	public String getBrnCode() {
		return brnCode;
	}
	public void setBrnCode(String brnCode) {
		this.brnCode = brnCode;
	}
	public String getBrnLabel() {
		return brnLabel;
	}
	public void setBrnLabel(String brnLabel) {
		this.brnLabel = brnLabel;
	}
	public String getConsDate() {
		return consDate;
	}
	public void setConsDate(String consDate) {
		this.consDate = consDate;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getDestinationBr() {
		return destinationBr;
	}
	public void setDestinationBr(String destinationBr) {
		this.destinationBr = destinationBr;
	}
	public String getDestinationBrKid() {
		return destinationBrKid;
	}
	public void setDestinationBrKid(String destinationBrKid) {
		this.destinationBrKid = destinationBrKid;
	}
	public String getDestinationBrLabel() {
		return destinationBrLabel;
	}
	public void setDestinationBrLabel(String destinationBrLabel) {
		this.destinationBrLabel = destinationBrLabel;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public boolean isDocType() {
		return docType;
	}
	public void setDocType(boolean docType) {
		this.docType = docType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	
	public void reset(ActionMapping mapping,HttpServletRequest request){
		 brnKid="" ;
		 brnCode=""; 
		 brnLabel="";
		 consDate="";
		 consNo="" ; 
		 destinationBr="" ;
		 destinationBrKid="";
		 destinationBrLabel="";
		 destination ="" ; 
		 weight="" ; 
	     dimension="";
		 docType=false;
		 docTypeValue="";
		 fwdArriv=false;
		 fwdArrivValue="";
		 fwdbrncode="";
		 bkngSearch="";
		 reason="";
		 uploadImg=null;
		 showDestbrn="";
		 searchConsNo="";
	}
}
