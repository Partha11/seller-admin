package com.supernova.selleradmin.task;

import android.os.AsyncTask;

import com.supernova.selleradmin.database.PendingDao;
import com.supernova.selleradmin.model.Pending;

public class InsertPending extends AsyncTask<Pending, Void, Void> {

    private PendingDao dao;

    public InsertPending(PendingDao dao) {

        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Pending... pending) {

        dao.insertPending(pending[0]);
        return null;
    }
}
