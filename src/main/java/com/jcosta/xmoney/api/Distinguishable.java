package com.jcosta.xmoney.api;

import java.io.Serializable;

public interface Distinguishable <T extends Serializable> {

    T getCode();
}
