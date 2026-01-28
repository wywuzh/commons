/*
 * Copyright 2015-2026 the original author or authors.
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
package io.github.wywuzh.commons.core.executor;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.wywuzh.commons.core.executor.exception.TransactionExecutionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * TransExecutor测试用例
 *
 * @author 伍章红
 * @since JDK 1.8
 */
@RunWith(MockitoJUnitRunner.class)
public class TransExecutorTest {

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private TransactionTemplate transactionTemplate;

    @InjectMocks
    private TransExecutor transExecutor;

    @Test
    public void testExecute_Success() {
        // Given
        TransUnit unit = mock(TransUnit.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transExecutor.execute(unit);

        // Then
        verify(unit, times(1)).execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecute_NullUnit() {
        // When & Then
        transExecutor.execute(null);
    }

    @Test
    public void testExecute_WithException() {
        // Given
        TransUnit unit = mock(TransUnit.class);
        doThrow(new RuntimeException("Business error")).when(unit).execute();
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenThrow(new RuntimeException("Business error"));

        // When & Then
        TransactionExecutionException exception = null;
        try {
            transExecutor.execute(unit);
        } catch (TransactionExecutionException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("事务执行失败"));
    }

    @Test
    public void testExecute_WithCustomAttributes() {
        // Given
        TransUnit unit = mock(TransUnit.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transExecutor.execute(unit, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_SERIALIZABLE, 120, true);

        // Then
        verify(unit, times(1)).execute();
    }

    @Test
    public void testExecuteInReadOnlyTransaction() {
        // Given
        TransUnit unit = mock(TransUnit.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transExecutor.executeInReadOnlyTransaction(unit);

        // Then
        verify(unit, times(1)).execute();
    }
}
