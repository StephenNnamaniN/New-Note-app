package com.nnamanistephen.jetnoteapp.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nnamanistephen.jetnoteapp.R
import com.nnamanistephen.jetnoteapp.components.NoteButton
import com.nnamanistephen.jetnoteapp.components.NoteInputText
import com.nnamanistephen.jetnoteapp.data.NotesDataSource
import com.nnamanistephen.jetnoteapp.model.Note
import com.nnamanistephen.jetnoteapp.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note)-> Unit,
    onUpdateNote: (Note) -> Unit
){

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column (modifier = Modifier.padding(6.dp)){
        TopAppBar(title = { 
            Text(text = stringResource(id = R.string.app_name))
        }, 
            actions = {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notification Icon")
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFA2A4A7))
        )

        // Content
        Column (modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                        char.isLetterOrDigit() || char.isWhitespace()
                    }) title = it
                })

            NoteInputText(
                modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = description,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char ->
                        char.isLetterOrDigit() || char.isWhitespace()
                        }) description = it
                })

            NoteButton(
                text = "Save",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()){
                        onAddNote(Note(
                            title = title,
                            description = description
                        ))
                        title = ""
                        description = ""
                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        HorizontalDivider(modifier = Modifier.padding(10.dp))

        LazyColumn {
            items(notes){ note ->
                NoteRow(note = note, onUpdateNote = {
                    onUpdateNote(note)
                }, onRemoveNote = {
                    onRemoveNote(note)
                })

            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onUpdateNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
){
    val title by remember {
        mutableStateOf(note.title)
    }

    val description by remember {
        mutableStateOf(note.description)
    }

    Surface (
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFA2A4A7),
        shadowElevation = 6.dp
    ){
        Column (
            modifier
                .clickable {
                    val updateNote = note.copy(
                        title = title,
                        description = description,
                    )
                    onUpdateNote(updateNote)
                }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start
        ){
            Row {
                Column {
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = note.description, style = MaterialTheme.typography.titleSmall)
                    Text(text = formatDate(note.entryDate.time),
                        style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onClick = { onRemoveNote(note) },
                    modifier = Modifier.padding(start = 150.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete icon")
                }
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun NoteScreenPreview(){
    NoteScreen(notes = NotesDataSource().loadNotes(), onAddNote = {}, onUpdateNote = {}, onRemoveNote = {})
}