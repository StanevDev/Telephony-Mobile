package edu.jam.telephony;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.jam.telephony.model.Service;
import edu.jam.telephony.model.ServiceType;
import edu.jam.telephony.model.TariffPlan;

public class TestData {

    static public List<Service> getServices(){
        List<Service> services = new ArrayList<>();

        services.add(new Service(0,new BigDecimal("124.00"),0, ServiceType.EXTERNAL_CALL,"Lorem"));
        services.add(new Service(0,new BigDecimal("24.50"),0, ServiceType.G3_INTERNET,"Neque"));
        services.add(new Service(0,new BigDecimal("114.00"),0, ServiceType.MMS,"Anyone"));
        services.add(new Service(0,new BigDecimal("4.00"),0, ServiceType.SMS,"Extremely"));
        return services;
    }

    static public List<TariffPlan> getTariffs(){
        List<TariffPlan> tariffPlans = new ArrayList<>();
        tariffPlans.add(new TariffPlan(0,null,null,null,"Lorem",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Accusamus",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Extremely",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Anyone",some));
        tariffPlans.add(new TariffPlan(0,null,null,null,"Neque",some));
        return tariffPlans;
    }

    static public String some = "But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or";

}
