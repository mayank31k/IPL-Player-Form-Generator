package com;

public class ParseItem {
    private String imgurl;
    private String title;
    private String playerid;

    public ParseItem() {
    }

    public ParseItem(String imgurl, String title, String playerid) {
        this.imgurl = imgurl;
        this.title = title;
        this.playerid = playerid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }
}

