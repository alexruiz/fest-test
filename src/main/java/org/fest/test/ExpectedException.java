/*
 * Created on Sep 7, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.test;

import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

/**
 * Allows in-test specification of expected exception types and messages.
 *
 * @author Alex Ruiz
 */
public class ExpectedException implements TestRule {
  private final org.junit.rules.ExpectedException delegate = org.junit.rules.ExpectedException.none();

  private ExpectedException() {
  }

  public static ExpectedException none() {
    return new ExpectedException();
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return delegate.apply(base, description);
  }

  public void expect(@NotNull Class<? extends Throwable> type, @NotNull String message) {
    expect(type);
    expectMessage(message);
  }

  public void expect(@NotNull Class<? extends Throwable> type) {
    delegate.expect(type);
  }

  public void expectMessage(@NotNull String message) {
    delegate.expectMessage(message);
  }

  public void expectMessageToContain(final @NotNull String... strings) {
    delegate.expectMessage(new TypeSafeMatcher<String>() {
      @Override
      public void describeTo(org.hamcrest.Description description) {
        description.appendText("containing: " + Arrays.toString(strings));
      }

      @Override
      public boolean matchesSafely(String item) {
        for (String s : strings) {
          if (!item.contains(s)) {
            return false;
          }
        }
        return true;
      }
    });
  }
}
