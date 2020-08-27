package ch.zotteljedi.onlineshop.firststep.service;

import java.util.List;
import javax.ejb.Local;

@Local
public interface MyFirstServiceLocal {
    String setMessage();
    List<String> getMessage();
}

