package net.paulgray.mocklti2.config;

import org.imsglobal.lti2.LTI2Config;
import org.springframework.stereotype.Component;

/**
 * Created by pgray on 8/21/14.
 */
@Component
public class MockLti2Config implements LTI2Config {

    @Override
    public String getGuid() {
        return "guid_from_config";
    }

    @Override
    public String getSupport_email() {
        return "support_email";
    }

    @Override
    public String getService_owner_id() {
        return "service_owner_id";
    }

    @Override
    public String getService_owner_owner_name() {
        return null;
    }

    @Override
    public String getService_owner_description() {
        return null;
    }

    @Override
    public String getService_owner_support_email() {
        return null;
    }

    @Override
    public String getService_provider_id() {
        return null;
    }

    @Override
    public String getService_provider_provider_name() {
        return null;
    }

    @Override
    public String getService_provider_description() {
        return null;
    }

    @Override
    public String getService_provider_support_email() {
        return null;
    }

    @Override
    public String getProduct_family_product_code() {
        return null;
    }

    @Override
    public String getProduct_family_vendor_code() {
        return null;
    }

    @Override
    public String getProduct_family_vendor_name() {
        return null;
    }

    @Override
    public String getProduct_family_vendor_description() {
        return null;
    }

    @Override
    public String getProduct_family_vendor_website() {
        return null;
    }

    @Override
    public String getProduct_family_vendor_contact() {
        return null;
    }

    @Override
    public String getProduct_info_product_name() {
        return null;
    }

    @Override
    public String getProduct_info_product_version() {
        return null;
    }

    @Override
    public String getProduct_info_product_description() {
        return null;
    }
}
