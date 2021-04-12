package org.arturkufa.importer;

import org.apache.flink.api.java.functions.KeySelector;
import org.arturkufa.importer.model.Mail;

public class MailKeySelector implements KeySelector<Mail, MailUserKey> {

    @Override
    public MailUserKey getKey(Mail value) throws Exception {
        MailUserKey mailUserKey = new MailUserKey();
        mailUserKey.setReceiverId(value.getReceiverId());
        mailUserKey.setSenderId(value.getSenderId());
        return mailUserKey;
    }
}
