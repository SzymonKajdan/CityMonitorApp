package com.inz.citymonitor.data.model

enum class ReportsType(val type:String) {
    VANDALISM("wandalizm"),
    HOLE_IN_THE_ROAD("dziura w drodze"),
    COLLISION("kolizja"),
    PNTH("człowiek potrzebujący pomocy"),
    INAPPROPRIATE_BEHAVIURS("niedozwolone zachowanie"),
    POTENTIAL_BULLYING("potencjalne łamanie prawa (zastraszanie)");

}