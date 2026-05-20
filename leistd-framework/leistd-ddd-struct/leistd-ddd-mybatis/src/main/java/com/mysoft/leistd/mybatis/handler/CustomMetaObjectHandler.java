package com.mysoft.leistd.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mysoft.leistd.domain.entities.auditing.*;
import com.mysoft.leistd.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CustomMetaObjectHandler implements MetaObjectHandler {

    final CurrentUser currentUser;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof CreationAuditedEntity || metaObject.getOriginalObject() instanceof CreationAuditedHasKeyEntity) {
            if (this.getFieldValByName("createdTime", metaObject) == null) {
                this.setFieldValByName("createdTime", new Date(), metaObject);
                if (currentUser.isAuthenticated()) {
                    String username = currentUser.getUsername();
                    if (StringUtils.isNotBlank(username) && this.getFieldValByName("creatorId", metaObject) == null) {
                        this.setFieldValByName("creatorId", username, metaObject);
                    }
                    String name = currentUser.getName();
                    if (StringUtils.isNotBlank(name) && this.getFieldValByName("creatorName", metaObject) == null) {
                        this.setFieldValByName("creatorName", name, metaObject);
                    }
                }
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof ModificationAuditedEntity || metaObject.getOriginalObject() instanceof ModificationAuditedHasKeyEntity) {
            this.setFieldValByName("lastModifiedTime", new Date(), metaObject);

            if (currentUser.isAuthenticated()) {
                    String username = currentUser.getUsername();
                    if (StringUtils.isNotBlank(username)) {
                        this.setFieldValByName("lastModifierId", username, metaObject);
                    }
                    String name = currentUser.getName();
                if (StringUtils.isNotBlank(name)) {
                    this.setFieldValByName("lastModifierName", name, metaObject);
                }
            }
        }

        if (metaObject.getOriginalObject() instanceof DeletionAuditedEntity || metaObject.getOriginalObject() instanceof DeletionAuditedHasKeyEntity) {
            if ((boolean) this.getFieldValByName("isDeleted", metaObject)) {
                this.setFieldValByName("deletedTime", new Date(), metaObject);

                if (currentUser.isAuthenticated()) {
                    String username = currentUser.getUsername();
                    if (StringUtils.isNotBlank(username)) {
                        this.setFieldValByName("deleterId", username, metaObject);
                    }
                    String name = currentUser.getName();
                    if (StringUtils.isNotBlank(name)) {
                        this.setFieldValByName("deleterName", name, metaObject);
                    }
                }
            }
        }
    }
}
