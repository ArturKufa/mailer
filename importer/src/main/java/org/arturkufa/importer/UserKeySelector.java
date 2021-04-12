package org.arturkufa.importer;

import org.apache.flink.api.java.functions.KeySelector;
import org.arturkufa.importer.model.User;

public class UserKeySelector implements KeySelector<User, MailUserKey> {
    @Override
    public MailUserKey getKey(User value) throws Exception {
        MailUserKey key = new MailUserKey();
        key.setUserId(value.getId());
        return key;
    }
}
