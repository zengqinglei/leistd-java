package com.mysoft.leistd.tracing.mdc;

import com.mysoft.leistd.mdc.MdcProvider;
import com.mysoft.leistd.tracing.CorrelationIdProvider;
import com.mysoft.leistd.tracing.constant.CorrelationIdLogConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CorrelationIdLogMdc {
    final CorrelationIdProvider correlationIdProvider;

    public void add(MdcProvider mdcProvider) {
        String correlationId = correlationIdProvider.get();
        mdcProvider.add(CorrelationIdLogConst.TRACE_ID, correlationId);
    }
}
