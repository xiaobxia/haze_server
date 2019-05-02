package com.info.web.pojo;

/**
 * Created by Phi on 2018/1/2.
 */
public class BorrowOrderLocationStatistics {
    private String location;

    /**
     * 申请数
     */
    private int count;
    /**
     * 通过数
     */
    private int passCount;
    /**
     * 失信数
     */
    private int badCreditCount;

    private int age99LaterCount;
    private int age99To92Count;
    private int age91To85Count;
    private int age84To76Count;
    private int age75To68Count;
    private int age68BeforeCount;

    private int age99LaterCountPassCount;
    private int age99To92CountPassCount;
    private int age91To85CountPassCount;
    private int age84To76CountPassCount;
    private int age75To68CountPassCount;
    private int age68BeforeCountPassCount;

    private int age99LaterCountBadCredit;
    private int age99To92CountBadCredit;
    private int age91To85CountBadCredit;
    private int age84To76CountBadCredit;
    private int age75To68CountBadCredit;
    private int age68BeforeCountBadCredit;

    /**
     * 申请年龄归档
     * @param age
     */
    public void addAgeCount(String age) {
        int i = 0;
        try {
            i = Integer.parseInt(age);
        } catch (Exception e) {
        }
        if (i > 1999) {
            age99LaterCount += 1;
        }
        if (i <= 1999 && i >= 1992) {
            age99To92Count += 1;
        }
        if (i <= 1991 && i >= 1985) {
            age91To85Count += 1;
        }
        if (i <= 1984 && i >= 1976) {
            age84To76Count += 1;
        }
        if (i <= 1975 && i >= 1968) {
            age75To68Count += 1;
        }
        if (i < 68) {
            age68BeforeCount += 1;
        }
    }

    /**
     * 放款年龄归档
     * @param age
     */
    public void addAgeCountPassCount(String age) {
        int i = 0;
        try {
            i = Integer.parseInt(age);
        } catch (Exception e) {
        }
        if (i > 1999) {
            age99LaterCountPassCount += 1;
        }
        if (i <= 1999 && i >= 1992) {
            age99To92CountPassCount += 1;
        }
        if (i <= 1991 && i >= 1985) {
            age91To85CountPassCount += 1;
        }
        if (i <= 1984 && i >= 1976) {
            age84To76CountPassCount += 1;
        }
        if (i <= 1975 && i >= 1968) {
            age75To68CountPassCount += 1;
        }
        if (i < 68) {
            age68BeforeCountPassCount += 1;
        }
    }

    /**
     * 失信年龄归档
     * @param age
     */
    public void addAgeCountBadCredit(String age) {
        int i = 0;
        try {
            i = Integer.parseInt(age);
        } catch (Exception e) {
        }
        if (i > 1999) {
            age99LaterCountBadCredit += 1;
        }
        if (i <= 1999 && i >= 1992) {
            age99To92CountBadCredit += 1;
        }
        if (i <= 1991 && i >= 1985) {
            age91To85CountBadCredit += 1;
        }
        if (i <= 1984 && i >= 1976) {
            age84To76CountBadCredit += 1;
        }
        if (i <= 1975 && i >= 1968) {
            age75To68CountBadCredit += 1;
        }
        if (i < 68) {
            age68BeforeCountBadCredit += 1;
        }
    }

    //通过率
    public String getPassRate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) passCount / (double) count);
    }
    //失信率
    public String getBadCreditRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) badCreditCount / (double) passCount);
    }

    /**
     * 申请占比
     * @return
     */
    public String getAge99LaterRate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99LaterCount / (double) count);
    }

    public String getAge99To92Rate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99To92Count / (double) count);
    }

    public String getAge91To85Rate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age91To85Count / (double) count);
    }

    public String getAge84To76Rate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age84To76Count / (double) count);
    }

    public String getAge75To68Rate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age75To68Count / (double) count);
    }

    public String getAge68BeforeRate() {
        if (count == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age68BeforeCount / (double) count);
    }

    /**
     * 通过占比
     * @return
     */
    public String getAge99LaterPassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99LaterCountPassCount / (double) passCount);
    }

    public String getAge99To92PassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99To92CountPassCount / (double) passCount);
    }

    public String getAge91To85PassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age91To85CountPassCount / (double) passCount);
    }

    public String getAge84To76PassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age84To76CountPassCount / (double) passCount);
    }

    public String getAge75To68PassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age75To68CountPassCount / (double) passCount);
    }

    public String getAge68BeforePassRate() {
        if (passCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age68BeforeCountPassCount / (double) passCount);
    }

    /**
     * 逾期占比
     * @return
     */
    public String getAge99LaterBadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99LaterCountBadCredit / (double) badCreditCount);
    }

    public String getAge99To92BadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age99To92CountBadCredit / (double) badCreditCount);
    }

    public String getAge91To85BadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age91To85CountBadCredit / (double) badCreditCount);
    }

    public String getAge84To76BadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age84To76CountBadCredit / (double) badCreditCount);
    }

    public String getAge75To68BadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age75To68CountBadCredit / (double) badCreditCount);
    }

    public String getAge68BeforeBadCreditRate() {
        if (badCreditCount == 0) {
            return "0.00";
        }
        return String.format("%.2f", (double) age68BeforeCountBadCredit / (double) badCreditCount);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public void setBadCreditCount(int badCreditCount) {
        this.badCreditCount = badCreditCount;
    }

    public int getAge99LaterCount() {
        return age99LaterCount;
    }

    public void setAge99LaterCount(int age99LaterCount) {
        this.age99LaterCount = age99LaterCount;
    }

    public int getAge99To92Count() {
        return age99To92Count;
    }

    public void setAge99To92Count(int age99To92Count) {
        this.age99To92Count = age99To92Count;
    }

    public int getAge91To85Count() {
        return age91To85Count;
    }

    public void setAge91To85Count(int age91To85Count) {
        this.age91To85Count = age91To85Count;
    }

    public int getAge84To76Count() {
        return age84To76Count;
    }

    public void setAge84To76Count(int age84To76Count) {
        this.age84To76Count = age84To76Count;
    }

    public int getAge75To68Count() {
        return age75To68Count;
    }

    public void setAge75To68Count(int age75To68Count) {
        this.age75To68Count = age75To68Count;
    }

    public int getAge68BeforeCount() {
        return age68BeforeCount;
    }

    public void setAge68BeforeCount(int age68BeforeCount) {
        this.age68BeforeCount = age68BeforeCount;
    }

    public int getBadCreditCount() {
        return badCreditCount;
    }

    public int getAge99LaterCountPassCount() {
        return age99LaterCountPassCount;
    }

    public void setAge99LaterCountPassCount(int age99LaterCountPassCount) {
        this.age99LaterCountPassCount = age99LaterCountPassCount;
    }

    public int getAge99To92CountPassCount() {
        return age99To92CountPassCount;
    }

    public void setAge99To92CountPassCount(int age99To92CountPassCount) {
        this.age99To92CountPassCount = age99To92CountPassCount;
    }

    public int getAge91To85CountPassCount() {
        return age91To85CountPassCount;
    }

    public void setAge91To85CountPassCount(int age91To85CountPassCount) {
        this.age91To85CountPassCount = age91To85CountPassCount;
    }

    public int getAge84To76CountPassCount() {
        return age84To76CountPassCount;
    }

    public void setAge84To76CountPassCount(int age84To76CountPassCount) {
        this.age84To76CountPassCount = age84To76CountPassCount;
    }

    public int getAge75To68CountPassCount() {
        return age75To68CountPassCount;
    }

    public void setAge75To68CountPassCount(int age75To68CountPassCount) {
        this.age75To68CountPassCount = age75To68CountPassCount;
    }

    public int getAge68BeforeCountPassCount() {
        return age68BeforeCountPassCount;
    }

    public void setAge68BeforeCountPassCount(int age68BeforeCountPassCount) {
        this.age68BeforeCountPassCount = age68BeforeCountPassCount;
    }

    public int getAge99LaterCountBadCredit() {
        return age99LaterCountBadCredit;
    }

    public void setAge99LaterCountBadCredit(int age99LaterCountBadCredit) {
        this.age99LaterCountBadCredit = age99LaterCountBadCredit;
    }

    public int getAge99To92CountBadCredit() {
        return age99To92CountBadCredit;
    }

    public void setAge99To92CountBadCredit(int age99To92CountBadCredit) {
        this.age99To92CountBadCredit = age99To92CountBadCredit;
    }

    public int getAge91To85CountBadCredit() {
        return age91To85CountBadCredit;
    }

    public void setAge91To85CountBadCredit(int age91To85CountBadCredit) {
        this.age91To85CountBadCredit = age91To85CountBadCredit;
    }

    public int getAge84To76CountBadCredit() {
        return age84To76CountBadCredit;
    }

    public void setAge84To76CountBadCredit(int age84To76CountBadCredit) {
        this.age84To76CountBadCredit = age84To76CountBadCredit;
    }

    public int getAge75To68CountBadCredit() {
        return age75To68CountBadCredit;
    }

    public void setAge75To68CountBadCredit(int age75To68CountBadCredit) {
        this.age75To68CountBadCredit = age75To68CountBadCredit;
    }

    public int getAge68BeforeCountBadCredit() {
        return age68BeforeCountBadCredit;
    }

    public void setAge68BeforeCountBadCredit(int age68BeforeCountBadCredit) {
        this.age68BeforeCountBadCredit = age68BeforeCountBadCredit;
    }
}
