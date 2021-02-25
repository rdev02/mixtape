package com.vhalitsyn.data;

import com.google.gson.annotations.SerializedName;

public enum EditActionType {
    @SerializedName("add")
    Add,
    @SerializedName("delete")
    Remove,
    @SerializedName("set_field")
    SetField
}
