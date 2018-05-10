package ro.orange.brisk.demo.beans;

public class Case {
    private String oacode;
    private String packageCode;
    private Agent agent;
    private String msisdn;

    public String getOacode() {
        return oacode;
    }

    public void setOacode(String oacode) {
        this.oacode = oacode;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
