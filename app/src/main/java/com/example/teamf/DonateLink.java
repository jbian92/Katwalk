package com.example.teamf;
import android.net.Uri;

import java.lang.String;

public class DonateLink {
    private int linkID;
    private String orgName;
    private Uri extURL;


    DonateLink(String orgName, String extURL) {
        this.orgName = orgName;
        this.extURL = Uri.parse(extURL);

    }

    public String getOrgName() { return orgName; }
    public Uri getURL() {return extURL;}

}
