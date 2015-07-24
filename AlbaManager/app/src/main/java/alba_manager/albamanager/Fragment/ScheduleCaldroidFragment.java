package alba_manager.albamanager.Fragment;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import alba_manager.albamanager.Util.ScheduleCaldroidAdapter;

public class ScheduleCaldroidFragment extends CaldroidFragment {
    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new ScheduleCaldroidAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }

}
