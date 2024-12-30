package com.nnamanistephen.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.nnamanistephen.jetnoteapp.screen.NoteScreen
import com.nnamanistephen.jetnoteapp.screen.NoteViewModel
import com.nnamanistephen.jetnoteapp.ui.theme.JetNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteAppTheme {
                Surface (color = MaterialTheme.colorScheme.background){

                    //val noteViewModel = viewModel<NoteViewModel>() //you can also instantiate the viewmodel like this
                    val noteViewModel: NoteViewModel by viewModels()
                   NotesApp(noteViewModel)

                }
            }
        }
    }
}


@Composable
fun NotesApp(noteViewModel: NoteViewModel){
    val notesList = noteViewModel.noteList.collectAsState().value

    NoteScreen(notes = notesList,
        onAddNote = {
            noteViewModel.addNote(it)
        },
        onRemoveNote = {
            noteViewModel.removeNote(it)
        },
        onUpdateNote = {
            noteViewModel.updateNote(it)
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetNoteAppTheme {
    }
}