package com.example.kdaproject001;

public class CreditInfo {


    private String majorCredit;
    private String generalCredit;
    private String cultureCredit;
    private String certificateName1;
    private String certificateCredit1;
    private String certificateName2;
    private String certificateCredit2;
    private String certificateName3;
    private String certificateCredit3;
    private String selfLearnCredit;
    private String etcCredit;
    private String writer;


    public CreditInfo(String certificateCredit1, String certificateCredit2, String certificateCredit3, String certificateName1, String certificateName2, String certificateName3,
                      String cultureCredit , String etcCredit, String majorCredit, String generalCredit, String selfLearnCredit , String writer) {
        this.majorCredit = majorCredit;
        this.generalCredit = generalCredit;
        this.cultureCredit = cultureCredit;
        this.certificateName1 = certificateName1;
        this.certificateCredit1 = certificateCredit1;
        this.certificateName2 = certificateName2;
        this.certificateCredit2 = certificateCredit2;
        this.certificateName3 = certificateName3;
        this.certificateCredit3 = certificateCredit3;
        this.selfLearnCredit = selfLearnCredit;
        this.etcCredit = etcCredit;
        this.writer = writer;
    }

    public String  getMajorCredit() {
        return majorCredit; }

    public void setMajorCredit(String majorCredit) {
        this.majorCredit = majorCredit; }

    public String getGeneralCredit() {
        return generalCredit;   }

    public void setGeneralCredit(String generalCredit) {
        this.generalCredit = generalCredit; }

    public String getCultureCredit() {
        return cultureCredit;   }

    public void setCultureCredit(String cultureCredit) {
        this.cultureCredit = cultureCredit; }

    public String getCertificateName1() {
        return certificateName1;    }

    public void setCertificateName1(String certificateName1) {
        this.certificateName1 = certificateName1;   }


    public String getCertificateCredit1() {
        return certificateCredit1;  }

    public void setCertificateCredit1(String certificateCredit1) {
        this.certificateCredit1 = certificateCredit1;   }


    public String getCertificateName2() {
        return certificateName2;    }

    public void setCertificateName2(String certificateName2) {
        this.certificateName2 = certificateName2;   }

    public String getCertificateCredit2() {
        return certificateCredit2;  }

    public void setCertificateCredit2(String certificateCredit2) {
        this.certificateCredit2 = certificateCredit2;   }


    public String getCertificateName3() {
        return certificateName3;    }

    public void setCertificateName3(String certificateName3) {
        this.certificateName3 = certificateName3;   }

    public String getCertificateCredit3() {
        return certificateCredit3;  }

    public void setCertificateCredit3(String certificateCredit3) {
        this.certificateCredit3 = certificateCredit3;   }

    public  String getSelfLearnCredit(){
        return  selfLearnCredit;    }

    public void setSelfLearnCredit(String selfLearnCredit){
        this.selfLearnCredit = selfLearnCredit;
    }

    public String getEtcCredit() {
        return etcCredit;   }

    public void setEtcCredit(String etcCredit) {
        this.etcCredit = etcCredit; }

    public String getWriter(){
        return writer;  }

    public void setWriter(String writer){
        this.writer = writer;   }
}
