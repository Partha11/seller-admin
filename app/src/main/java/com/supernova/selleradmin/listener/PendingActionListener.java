package com.supernova.selleradmin.listener;

import com.supernova.selleradmin.model.Pending;

public interface PendingActionListener {

    void delete(Pending pending);
    void resend(Pending pending);
}
