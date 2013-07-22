/*
 * Copyright (c) 2009-2013, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.admindistribution.configuration.jmx;

import gov.hhs.fha.nhinc.admindistribution._20.entity.EntityAdministrativeDistributionSecured_g1;
import gov.hhs.fha.nhinc.admindistribution._20.entity.EntityAdministrativeDistribution_g1;
import gov.hhs.fha.nhinc.admindistribution._20.nhin.NhinAdministrativeDistribution_g1;
import gov.hhs.fha.nhinc.admindistribution.inbound.InboundAdminDistribution;
import gov.hhs.fha.nhinc.admindistribution.outbound.OutboundAdminDistribution;

import javax.servlet.ServletContext;

/**
 * The Class AdminDistribution20WebServices.
 *
 * @author msw
 */
public class AdminDistribution20WebServices extends AbstractAdminDistributionWebServicesMXBean {

    /** The Constant NHIN_AD_BEAN_NAME. */
    private static final String NHIN_AD_BEAN_NAME = "NhinAdministrativeDistributionBean_g1";

    /** The Constant ENTITY_UNSECURED_AD_BEAN_NAME. */
    private static final String ENTITY_UNSECURED_AD_BEAN_NAME = "EntityAdministrativeDistributionBean_g1";

    /** The Constant ENTITY_SECURED_AD_BEAN_NAME. */
    private static final String ENTITY_SECURED_AD_BEAN_NAME = "EntityAdministrativeDistributionSecuredBean_g1";
    
    /**
     * Instantiates a new admin distribution20 web services.
     *
     * @param sc the sc
     */
    public AdminDistribution20WebServices(ServletContext sc) {
        super(sc);
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.WebServicesMXBean#isInboundPassthru()
     */
    @Override
    public boolean isInboundPassthru() {
        boolean isPassthru = false;
        NhinAdministrativeDistribution_g1 nhinAD = retrieveBean(NhinAdministrativeDistribution_g1.class, getNhinBeanName());
        InboundAdminDistribution inboundAD = nhinAD.getInboundAdminDistribution();
        if (DEFAULT_INBOUND_PASSTHRU_IMPL_CLASS_NAME.equals(inboundAD.getClass().getName())) {
            isPassthru = true;
        }
        return isPassthru;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.WebServicesMXBean#isOutboundPassthru()
     */
    @Override
    public boolean isOutboundPassthru() {
        boolean isPassthru = false;
        EntityAdministrativeDistribution_g1 entityAD = retrieveBean(EntityAdministrativeDistribution_g1.class, getEntityUnsecuredBeanName());
        OutboundAdminDistribution outboundAD = entityAD.getOutboundAdminDistribution();
        if (DEFAULT_OUTBOUND_PASSTHRU_IMPL_CLASS_NAME.equals(outboundAD.getClass().getName())) {
            isPassthru = true;
        }
        return isPassthru;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.AbstractWebServicesMXBean#getNhinBeanName()
     */
    @Override
    protected String getNhinBeanName() {
        return NHIN_AD_BEAN_NAME;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.AbstractWebServicesMXBean#getEntityUnsecuredBeanName()
     */
    @Override
    protected String getEntityUnsecuredBeanName() {
        return ENTITY_UNSECURED_AD_BEAN_NAME;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.AbstractWebServicesMXBean#getEntitySecuredBeanName()
     */
    @Override
    protected String getEntitySecuredBeanName() {
        return ENTITY_SECURED_AD_BEAN_NAME;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.AbstractWebServicesMXBean#configureInboundImpl(java.lang.String)
     */
    @Override
    public void configureInboundImpl(String className) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        NhinAdministrativeDistribution_g1 nhinAD = retrieveBean(NhinAdministrativeDistribution_g1.class, getNhinBeanName());
        InboundAdminDistribution inboundAdminDistribution = retrieveDependency(InboundAdminDistribution.class, className);
        
        nhinAD.setInboundAdminDistribution(inboundAdminDistribution);
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.configuration.jmx.AbstractWebServicesMXBean#configureOutboundImpl(java.lang.String)
     */
    @Override
    public void configureOutboundImpl(String className) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        EntityAdministrativeDistribution_g1 entityADUnsecured = retrieveBean(EntityAdministrativeDistribution_g1.class, getEntityUnsecuredBeanName());
        EntityAdministrativeDistributionSecured_g1 entityADSecured = retrieveBean(EntityAdministrativeDistributionSecured_g1.class, getEntitySecuredBeanName());
        
        OutboundAdminDistribution outboundAdminDistribution = retrieveDependency(OutboundAdminDistribution.class, className);
        
        entityADSecured.setOutboundAdminDistribution(outboundAdminDistribution);
        entityADUnsecured.setOutboundAdminDistribution(outboundAdminDistribution);
    }

}