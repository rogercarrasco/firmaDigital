package pe.gob.pcm.sgd.firmaperu.clientewebtest.dto;

import java.io.Serializable;

public class SignatureParameters implements Serializable {

    private String signatureFormat;
    private String signatureLevel; 
    private String signaturePackaging;
    private String documentToSign;
    private String certificateFilter;
    private String webTsa;
    private String userTsa;
    private String passwordTsa;
    private String theme;
    private boolean visiblePosition;
    private String contactInfo;
    private String signatureReason;
    private boolean bachtOperation;
    private boolean oneByOne;
    private int signatureStyle;
    private String imageToStamp;
    private int stampTextSize;
    private int stampWordWrap;
    private String role;
    private int stampPage;
    private int positionx;
    private int positiony;
    private String uploadDocumentSigned;
    private String token;

    public SignatureParameters() {
        this.signatureFormat = "";
        this.signatureLevel = "";
        this.signaturePackaging = "";
        this.documentToSign = "";
        this.certificateFilter = ".*";
        this.webTsa = "";
        this.userTsa = "";
        this.passwordTsa = "";
        this.theme = "claro";
        this.visiblePosition = true;
        this.contactInfo = "";
        this.signatureReason = "";
        this.bachtOperation = false;
        this.oneByOne = true;
        this.signatureStyle = 0;
        this.imageToStamp = "";
        this.stampTextSize = 5;
        this.stampWordWrap = 34;
        this.role = "";
        this.stampPage = 1;
        this.positionx = 0;
        this.positiony = 0;
        this.uploadDocumentSigned = "";
        this.token = "";

    }

    public String getSignatureFormat() {
        return signatureFormat;
    }

    public void setSignatureFormat(String signatureFormat) {
        this.signatureFormat = signatureFormat;
    }

    public String getSignatureLevel() {
        return signatureLevel;
    }

    public void setSignatureLevel(String signatureLevel) {
        this.signatureLevel = signatureLevel;
    }

    public String getSignaturePackaging() {
        return signaturePackaging;
    }

    public void setSignaturePackaging(String signaturePackaging) {
        this.signaturePackaging = signaturePackaging;
    }

    public String getDocumentToSign() {
        return documentToSign;
    }

    public void setDocumentToSign(String documentToSign) {
        this.documentToSign = documentToSign;
    }

    public String getCertificateFilter() {
        return certificateFilter;
    }

    public void setCertificateFilter(String certificateFilter) {
        this.certificateFilter = certificateFilter;
    }

    public String getWebTsa() {
        return webTsa;
    }

    public void setWebTsa(String webTsa) {
        this.webTsa = webTsa;
    }

    public String getUserTsa() {
        return userTsa;
    }

    public void setUserTsa(String userTsa) {
        this.userTsa = userTsa;
    }

    public String getPasswordTsa() {
        return passwordTsa;
    }

    public void setPasswordTsa(String passwordTsa) {
        this.passwordTsa = passwordTsa;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isVisiblePosition() {
        return visiblePosition;
    }

    public void setVisiblePosition(boolean visiblePosition) {
        this.visiblePosition = visiblePosition;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSignatureReason() {
        return signatureReason;
    }

    public void setSignatureReason(String signatureReason) {
        this.signatureReason = signatureReason;
    }

    public boolean isBachtOperation() {
        return bachtOperation;
    }

    public void setBachtOperation(boolean bachtOperation) {
        this.bachtOperation = bachtOperation;
    }

    public boolean isOneByOne() {
        return oneByOne;
    }

    public void setOneByOne(boolean oneByOne) {
        this.oneByOne = oneByOne;
    }

    public int getSignatureStyle() {
        return signatureStyle;
    }

    public void setSignatureStyle(int signatureStyle) {
        this.signatureStyle = signatureStyle;
    }

    public String getImageToStamp() {
        return imageToStamp;
    }

    public void setImageToStamp(String imageToStamp) {
        this.imageToStamp = imageToStamp;
    }

    public int getStampTextSize() {
        return stampTextSize;
    }

    public void setStampTextSize(int stampTextSize) {
        this.stampTextSize = stampTextSize;
    }

    public int getStampWordWrap() {
        return stampWordWrap;
    }

    public void setStampWordWrap(int stampWordWrap) {
        this.stampWordWrap = stampWordWrap;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStampPage() {
        return stampPage;
    }

    public void setStampPage(int stampPage) {
        this.stampPage = stampPage;
    }

    public int getPositionx() {
        return positionx;
    }

    public void setPositionx(int positionx) {
        this.positionx = positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public void setPositiony(int positiony) {
        this.positiony = positiony;
    }

    public String getUploadDocumentSigned() {
        return uploadDocumentSigned;
    }

    public void setUploadDocumentSigned(String uploadDocumentSigned) {
        this.uploadDocumentSigned = uploadDocumentSigned;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
