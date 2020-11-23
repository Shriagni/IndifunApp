package com.deificindia.indifun1.bindingmodals.userlevel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLevelResult {

@SerializedName("level")
@Expose
private String level;
@SerializedName("my_xp")
@Expose
private String myXp;
@SerializedName("to_next_level")
@Expose
private Integer toNextLevel;

public String getLevel() {
return level;
}

public void setLevel(String level) {
this.level = level;
}

public String getMyXp() {
return myXp;
}

public void setMyXp(String myXp) {
this.myXp = myXp;
}

public Integer getToNextLevel() {
return toNextLevel;
}

public void setToNextLevel(Integer toNextLevel) {
this.toNextLevel = toNextLevel;
}

}