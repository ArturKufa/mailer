package org.arturkufa.importer.mappers;

import io.vavr.Tuple2;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;
import org.arturkufa.importer.model.Mail;
import org.arturkufa.importer.model.User;

public  class MailReceiverMap extends RichCoFlatMapFunction<User, Mail, Tuple2<User, Mail>> {
    // keyed, managed state
    private ValueState<User> userState;
    private ValueState<Mail> mailState;

    @Override
    public void open(Configuration config) {
        userState = getRuntimeContext().getState(new ValueStateDescriptor<>("saved user", User.class));
        mailState = getRuntimeContext().getState(new ValueStateDescriptor<>("saved mail by receiver", Mail.class));
    }

    @Override
    public void flatMap1(User value, Collector<Tuple2<User, Mail>> out) throws Exception {
        Mail mail = mailState.value();
        if(mail != null) {
            out.collect(new Tuple2(value, mail));
        } else {
            userState.update(value);
        }
    }

    @Override
    public void flatMap2(Mail value, Collector<Tuple2<User, Mail>> out) throws Exception {
        User user = userState.value();
        if(user != null) {
            out.collect(new Tuple2(user, value));
        } else {
            mailState.update(value);
        }
    }


//
//    @Override
//    public void flatMap1(User ride, Collector<Tuple2<TaxiRide, TaxiFare>> out) throws Exception {
//        TaxiFare fare = mailState.value();
//        if (fare != null) {
//            mailState.clear();
//            out.collect(new Tuple2(ride, fare));
//        } else {
//            userState.update(ride);
//        }
//    }
//
//    @Override
//    public void flatMap2(TaxiFare fare, Collector<Tuple2<TaxiRide, TaxiFare>> out) throws Exception {
//        TaxiRide ride = userState.value();
//        if (ride != null) {
//            userState.clear();
//            out.collect(new Tuple2(ride, fare));
//        } else {
//            mailState.update(fare);
//        }
//    }
}

