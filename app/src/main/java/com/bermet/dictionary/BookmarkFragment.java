package com.bermet.dictionary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class BookmarkFragment extends Fragment {
    private FragmentListener listener;


    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        ListView bookmarkList = (ListView) view.findViewById(R.id.bookmarkList);
        final BookmarkAdapter adapter = new BookmarkAdapter(getActivity(), getListOfWords());
        bookmarkList.setAdapter(adapter);
        adapter.setOnItemClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                if (listener != null)

                listener.OnItemClick(String.valueOf(adapter.getItem(position)));


            }
        });
        adapter.setOnItemDeleteClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                 adapter.removeItem(position);
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setOnFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }

    String[] getListOfWords() {
        String[] source = new String[]{
                "A",
                "asmus",
                "ab: initio",
                "abacillation",
                "abacillation of bacillary excretion",
                "abacterial",
                "abactio",
                "abaissement ",
                "abalienatio: mentis",
                "abalienation ",
                "abapical",
                "abaptiston",
                "abarognosis",
                "abarthrosis",
                "abarticular",
                "abarticulation",
                "abasement",
                "abasia",
                "abasia choreic ",
                "abasia spastic ",
                "abasia trembling [trepidant] ",
                "abasia-astasia",
                "abatardissement",
                "abate",
                "abatement ",
                "abatement pollution",
                "abaxial",
                "abbau",
                "abbokinase",
                "abbreviated ",
                "abdomen",
                "abdomen inflatum",
                "abdomen  obstipum",
                "abdomen  tumidum",
                "abdomen accordion ",
                "acute abdomen",
                "abdomen boat-shaped",
                "abdomen empty",
                "flat abdomen",
                "fluid filled",
                "frog abdomen",
                "immobile abdomen",
                "abdomen lax",
                "mobile abdomen",
                "non-tender abdomen",
                "open abdomen",
                "pendulous abdomen",
                "protuberant abdomen",
                "retracted abdomen",
                "septic abdomen",
                "silent abdomen",
                "tender abdomen",
                "tense abdome",
                "upper abdomen",
                "abdominalgia",
                "periodic abdominalgia",
                "abdominocentesis",
                "abdominocyesis",
                "abdominogenital",
                "abdominohysterectomy",
                "abdominohysterotomy",
                "abdominometry",
                "abdominoperineal",
                "abdominoplasty",
                "abdominoscopy",
                "abdominoscrotal",
                "abdominothoracic",
                "abdominouterotomy см. abdominohysterotomy",
                "abdominovaginal",
                "abducens",
                "abducent",
                "abduction",
                "abduction forced",
                "abductor",
                "abenteric",
                "abepithymia ",
                "aberrant",
                "aberration",



        };
        return source;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear,menu);
    }
}