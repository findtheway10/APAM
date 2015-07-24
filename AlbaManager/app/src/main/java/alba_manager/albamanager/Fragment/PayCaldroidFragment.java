package alba_manager.albamanager.Fragment;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import alba_manager.albamanager.Util.PayCaldroidAdapter;


public class PayCaldroidFragment extends CaldroidFragment {
    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new PayCaldroidAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }

}
