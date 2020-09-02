package ch.zotteljedi.onlineshop.firststep.service;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MyFirstServiceLocal extends Serializable {
    String setMessage();
    List<String> getMessage();
}

