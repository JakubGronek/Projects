import os
import sqlite3
import PySimpleGUI as sg


database = os.getcwd()+"/spis.db"
create_table = ("CREATE TABLE IF NOT EXISTS spis ("
                "id integer PRIMARY KEY AUTOINCREMENT, "
                "tytul text not null, "
                "strony text, "
                "numer text)")
sg.set_options(font=("Arial Bold", 24), text_color='black')
listboxes = [
    [sg.Listbox(
            values=[], enable_events=True, no_scrollbar=True, size=(40, 20), key="TYTUL", pad=(0, 0)
        ),
        sg.Listbox(
            values=[], enable_events=True, no_scrollbar=True, size=(5, 20), key="STRONY", pad=(0, 0)
        ),
        sg.Listbox(
            values=[], enable_events=True, no_scrollbar=True, size=(5, 20), key="KOD", pad=(0, 0)
        )]
]
szukaj = [
    [
        sg.Text("SZUKAJ: "),
        sg.In(size=(35, 1), enable_events=True, key="TEKST")
    ],
    [sg.HorizontalSeparator(color="BLACK")],
    [
        sg.Text("TYTUŁ", justification='left'),
        sg.Text("SZT.  ", justification='right', expand_x=True),
        sg.Text("KOD  ", justification='right')
    ],
    [
        sg.Column(listboxes, scrollable=True, vertical_scroll_only=True, key="COLUMN")
    ]
]
dodaj = [
    [
        sg.Text("TYTUŁ"),
        sg.In(size=(35, 1), key="IN_TYTUL", do_not_clear=False, focus=True)

    ],
    [
        sg.Text("LICZBA STRON"),
        sg.In(size=(2, 1), key="IN_STRON", do_not_clear=False)
    ],
    [
        sg.Text("KOD"),
        sg.In(size=(5, 1), key="IN_KOD", do_not_clear=False)
    ],
    [
        sg.Button("DODAJ", bind_return_key=True),
        sg.Text(text="", key="RESULT")
    ]
]
layout = [
    [sg.TabGroup([[sg.Tab('SZUKAJ', szukaj), sg.Tab("DODAJ", dodaj)]])]
]


def create_connection(db_file):
    conn = None
    try:
        conn = sqlite3.connect(db_file)
        return conn
    except Exception as e:
        print(e)
    return conn


def create_tables(conn: sqlite3.Connection, create):
    try:
        c = conn.cursor()
        c.execute(create)
    except Exception as e:
        print(e)


def dodaj(conn: sqlite3.Connection, dane):
    sql = "INSERT INTO spis (tytul, strony, numer) VALUES (?,?,?)"
    try:
        c = conn.cursor()
        c.execute(sql, dane)
        conn.commit()
    except Exception as e:
        print(e)


def znajdz(conn: sqlite3.Connection, tytul):
    tytul = "%"+tytul+"%"
    sql = f"SELECT tytul, strony, numer FROM spis where tytul like '{tytul}'"
    try:
        c = conn.cursor()
        c.execute(sql)
        return c.fetchall()
    except Exception as e:
        print(e)


if __name__ == '__main__':
    conn = create_connection(database)
    create_tables(conn, create_table)
    window = sg.Window("RZUTNIK LISTA", layout)
    while True:
        event, values = window.read()
        if event == sg.WINDOW_CLOSED:
            break
        if event == "TEKST":
            out = znajdz(conn, values["TEKST"])
            tytul = [x[0] for x in out]
            strony = [x[1] for x in out]
            kod = [x[2] for x in out]
            window["TYTUL"].update(tytul)
            window["STRONY"].update(strony)
            window["KOD"].update(kod)
        if event == "DODAJ":
            tytul = values["IN_TYTUL"].upper()
            strony = values["IN_STRON"]
            kod = values["IN_KOD"]
            if tytul != "" and strony != "" and kod != "":
                dodaj(conn, [tytul, strony, kod])
                window["RESULT"].update("POMYŚLNIE DODANO")
                window.refresh()
                window["IN_TYTUL"].set_focus(force=True)
            else:
                window["RESULT"].update("BRAKUJĄCE DANE")
    window.close()