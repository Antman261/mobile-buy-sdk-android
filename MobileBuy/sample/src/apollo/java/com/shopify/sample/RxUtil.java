/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2015 Shopify Inc.
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package com.shopify.sample;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.internal.Optional;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.exceptions.Exceptions;

import static com.shopify.sample.util.Util.fold;

public final class RxUtil {

  public static <T> Single<Response<T>> rxApolloCall(final ApolloCall<T> call) {
    return Single.create(emitter -> {
      emitter.setCancellable(call::cancel);
      try {
        emitter.onSuccess(call.execute());
      } catch (Exception e) {
        Exceptions.throwIfFatal(e);
        emitter.onError(e);
      }
    });
  }

  public static <T> SingleTransformer<Response<Optional<T>>, T> queryResponseTransformer() {
    return upstream -> upstream.flatMap(response -> {
      if (response.errors().isEmpty()) {
        return Single.just(response.data().get());
      } else {
        String errorMessage = fold(new StringBuilder(), response.errors(),
          (builder, error) -> builder.append(error.message()).append("\n")).toString();
        return Single.error(new RuntimeException(errorMessage));
      }
    });
  }

  private RxUtil() {
  }
}