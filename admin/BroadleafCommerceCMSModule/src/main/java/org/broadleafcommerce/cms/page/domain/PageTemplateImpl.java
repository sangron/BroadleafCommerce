/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.broadleafcommerce.cms.page.domain;

import javax.persistence.*;

import java.util.List;

import org.broadleafcommerce.cms.field.domain.FieldGroup;
import org.broadleafcommerce.cms.field.domain.FieldGroupImpl;
import org.broadleafcommerce.cms.locale.domain.Locale;
import org.broadleafcommerce.cms.locale.domain.LocaleImpl;
import org.broadleafcommerce.presentation.AdminPresentation;
import org.broadleafcommerce.presentation.AdminPresentationClass;
import org.broadleafcommerce.presentation.PopulateToOneFieldsEnum;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

/**
 * Created by bpolster.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE_TEMPLATE")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blCMSElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
public class PageTemplateImpl implements PageTemplate {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PageTemplateId", strategy = GenerationType.TABLE)
    @TableGenerator(name = "PageTemplateId", table = "SEQUENCE_GENERATOR", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "PageTemplateImpl", allocationSize = 10)
    @Column(name = "PAGE_TEMPLATE_ID")
    @AdminPresentation(friendlyName = "Template Id", hidden = true, readOnly = true)
    protected Long id;

    @Column (name = "TEMPLATE_NAME")
    @AdminPresentation(friendlyName = "Template Name", prominent = true)
    protected String templateName;

    @Column (name = "TEMPLATE_DESCRIPTION")
    protected String templateDescription;

    @Column (name = "TEMPLATE_PATH")
    @AdminPresentation(friendlyName = "Template Path", hidden = true,readOnly = true)
    protected String templatePath;

    @ManyToOne(targetEntity = LocaleImpl.class)
    @JoinColumn(name = "LOCALE_ID")
    protected Locale locale;

    @ManyToMany(targetEntity = FieldGroupImpl.class, cascade = {CascadeType.ALL})
    @JoinTable(name = "BLC_PGTMPLT_FLDGRP_XREF", joinColumns = @JoinColumn(name = "PAGE_TEMPLATE_ID", referencedColumnName = "PAGE_TEMPLATE_ID"), inverseJoinColumns = @JoinColumn(name = "FIELD_GROUP_ID", referencedColumnName = "FIELD_GROUP_ID"))
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blCMSElements")
    @OrderColumn(name = "GROUP_ORDER")
    @BatchSize(size = 20)
    protected List<FieldGroup> fieldGroups;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String getTemplateDescription() {
        return templateDescription;
    }

    @Override
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public List<FieldGroup> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<FieldGroup> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }
}

