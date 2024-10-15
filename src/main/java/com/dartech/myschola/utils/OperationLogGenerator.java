package com.dartech.myschola.utils;

import com.dartech.myschola.entity.BaseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class OperationLogGenerator<T extends BaseEntity> {

    public T generateCreationLog(T object) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String connectedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        object.setCreatedBy(connectedUsername);
        object.setCreatedAt(timeStamp);
        return object;
    }

    public T generateUpdateLog(T object) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String connectedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        object.setLastUpdatedBy(connectedUsername);
        object.setLastUpdateAt(timeStamp);
        return object;
    }
}
