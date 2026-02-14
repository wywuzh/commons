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

import java.util.concurrent.Callable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import io.github.wywuzh.commons.core.executor.exception.TransactionalTaskException;

/**
 * TransactionalTaskExecutor测试用例
 *
 * @author 伍章红
 * @since JDK 1.8
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionalTaskExecutorTest {

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private TransactionTemplate transactionTemplate;

    @InjectMocks
    private TransactionalTaskExecutor transactionalTaskExecutor;

    @Test
    public void testExecuteRunnable_Success() throws Exception {
        // Given
        Runnable task = mock(Runnable.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transactionalTaskExecutor.execute(task);

        // Then
        verify(task, times(1)).run();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteRunnable_NullTask() {
        // When & Then
        transactionalTaskExecutor.execute((Runnable) null);
    }

    @Test
    public void testExecuteCallable_Success() throws Exception {
        // Given
        Callable<String> task = mock(Callable.class);
        when(task.call()).thenReturn("success");
        when(transactionTemplate.execute(any(TransactionCallback.class))).thenAnswer(invocation -> {
            TransactionCallback<String> callback = invocation.getArgument(0);
            return callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
        });

        // When
        String result = transactionalTaskExecutor.execute(task);

        // Then
        assertEquals("success", result);
        verify(task, times(1)).call();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteCallable_NullTask() {
        // When & Then
        transactionalTaskExecutor.execute((Runnable) null);
    }

    @Test
    public void testExecuteCallable_WithCheckedException() throws Exception {
        // Given
        Callable<String> task = mock(Callable.class);
        when(task.call()).thenThrow(new Exception("Checked exception"));
        when(transactionTemplate.execute(any(TransactionCallback.class))).thenThrow(new TransactionalTaskException("Callable任务执行异常", new Exception("Checked exception")));

        // When & Then
        TransactionalTaskException exception = null;
        try {
            transactionalTaskExecutor.execute(task);
        } catch (TransactionalTaskException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Callable任务执行异常"));
    }

    @Test(expected = RuntimeException.class)
    public void testExecuteCallable_WithRuntimeException() throws Exception {
        // Given
        Callable<String> task = mock(Callable.class);
        RuntimeException runtimeException = new RuntimeException("Runtime exception");
        when(task.call()).thenThrow(runtimeException);
        when(transactionTemplate.execute(any(TransactionCallback.class))).thenThrow(runtimeException);

        // When & Then
        transactionalTaskExecutor.execute(task);
    }

    @Test
    public void testExecuteInReadOnlyTransaction_Runnable() {
        // Given
        Runnable task = mock(Runnable.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transactionalTaskExecutor.executeInReadOnlyTransaction(task);

        // Then
        verify(task, times(1)).run();
    }

    @Test
    public void testExecuteInReadOnlyTransaction_Callable() throws Exception {
        // Given
        Callable<String> task = mock(Callable.class);
        when(task.call()).thenReturn("readonly-result");
        when(transactionTemplate.execute(any(TransactionCallback.class))).thenAnswer(invocation -> {
            TransactionCallback<String> callback = invocation.getArgument(0);
            return callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
        });

        // When
        String result = transactionalTaskExecutor.executeInReadOnlyTransaction(task);

        // Then
        assertEquals("readonly-result", result);
    }

    @Test
    public void testExecuteWithCustomAttributes_Runnable() {
        // Given
        Runnable task = mock(Runnable.class);
        when(transactionTemplate.execute(any(TransactionCallbackWithoutResult.class))).thenAnswer(invocation -> {
            TransactionCallbackWithoutResult callback = invocation.getArgument(0);
            callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
            return null;
        });

        // When
        transactionalTaskExecutor.executeWithCustomAttributes(task, TransactionDefinition.PROPAGATION_REQUIRED, TransactionDefinition.ISOLATION_READ_COMMITTED, 60, true);

        // Then
        verify(task, times(1)).run();
    }

    @Test
    public void testExecuteWithCustomAttributes_Callable() throws Exception {
        // Given
        Callable<String> task = mock(Callable.class);
        when(task.call()).thenReturn("custom-result");
        when(transactionTemplate.execute(any(TransactionCallback.class))).thenAnswer(invocation -> {
            TransactionCallback<String> callback = invocation.getArgument(0);
            return callback.doInTransaction(mock(org.springframework.transaction.TransactionStatus.class));
        });

        // When
        String result = transactionalTaskExecutor.executeWithCustomAttributes(task, TransactionDefinition.PROPAGATION_REQUIRES_NEW, TransactionDefinition.ISOLATION_SERIALIZABLE, 120, false);

        // Then
        assertEquals("custom-result", result);
    }
}
