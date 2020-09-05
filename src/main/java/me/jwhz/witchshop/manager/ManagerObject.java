package me.jwhz.witchshop.manager;

import me.jwhz.witchshop.WitchShop;

public abstract class ManagerObject<I> {

    protected WitchShop core = WitchShop.getInstance();

    public abstract I getIdentifier();

}