/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */

package org.elasticsearch.xpack.core.indexlifecycle;

import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.test.AbstractXContentTestCase;
import org.elasticsearch.test.EqualsHashCodeTestUtils;
import org.elasticsearch.xpack.core.indexlifecycle.ReplicasAllocatedStep.Info;

import java.io.IOException;

public class ReplicasAllocatedStepInfoTests extends AbstractXContentTestCase<ReplicasAllocatedStep.Info> {

    @Override
    protected Info createTestInstance() {
        return new Info(randomNonNegativeLong(), randomBoolean());
    }

    @Override
    protected Info doParseInstance(XContentParser parser) throws IOException {
        return Info.PARSER.apply(parser, null);
    }

    @Override
    protected boolean supportsUnknownFields() {
        return false;
    }

    public final void testEqualsAndHashcode() {
        for (int runs = 0; runs < NUMBER_OF_TEST_RUNS; runs++) {
            EqualsHashCodeTestUtils.checkEqualsAndHashCode(createTestInstance(), this::copyInstance, this::mutateInstance);
        }
    }

    protected final Info copyInstance(Info instance) throws IOException {
        return new Info(instance.getActualReplicas(), instance.allShardsActive());
    }

    protected Info mutateInstance(Info instance) throws IOException {
        long actualReplicas = instance.getActualReplicas();
        boolean allShardsActive = instance.allShardsActive();
        if (randomBoolean()) {
            actualReplicas += between(1, 20);
        } else {
            allShardsActive = allShardsActive == false;
        }
        return new Info(actualReplicas, allShardsActive);
    }

}
