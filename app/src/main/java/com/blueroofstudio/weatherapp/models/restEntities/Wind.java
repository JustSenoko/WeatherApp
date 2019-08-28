package com.blueroofstudio.weatherapp.models.restEntities;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Wind {
    @SerializedName("code") public float speed;
    @SerializedName("deg") private float deg;
}
