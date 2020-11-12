/**
 * @program school-bus-cloud
 * @description: CommonBindingResult
 * @author: mf
 * @create: 2020/11/03 14:15
 */

package com.dream.bus.param;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class CommonBindingResult {
    public static String getErrors(BindingResult bindingResult) {
        ArrayList<String> errors = bindingResult.getFieldErrors()
                .stream()
                .collect(() -> new ArrayList<String>(), (l, o) -> {
                    l.add(o.getDefaultMessage());
                }, List::addAll);
        return errors.toString();
    }
}
