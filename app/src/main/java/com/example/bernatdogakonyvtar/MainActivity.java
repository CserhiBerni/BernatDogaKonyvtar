package com.example.bernatdogakonyvtar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTile, etAuthor, etPages;
    Button bAdd;
    ListView lvBooks;
    ArrayList<Book> bookList;
    BookAdapter bookAdapter;
    ListView listViewBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTile = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etPages = findViewById(R.id.etPages);
        bAdd = findViewById(R.id.bAdd);
        lvBooks = findViewById(R.id.lvBooks);

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        listViewBooks.setAdapter(bookAdapter);

        bAdd.setOnClickListener(view -> {
            String title = etTile.getText().toString();
            String author = etAuthor.getText().toString();
            int pages = Integer.parseInt(String.valueOf(etPages.getText()));

            if (!title.isEmpty() && !author.isEmpty() && pages >= 50) {
                bookList.add(new Book(title, author, pages));
                bookAdapter.notifyDataSetChanged();
                etTile.setText("");
                etAuthor.setText("");
                etPages.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Vagy hiányzik adat, vagy túl kevés oldalas a könyv.", Toast.LENGTH_SHORT).show();
            }
        });

        listViewBooks.setOnItemClickListener((adapterView, view, position, id) -> {
            Book selectedBook = bookList.get(position);
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("bookTitle", selectedBook.getTitle());
            intent.putExtra("bookAuthor", selectedBook.getAuthor());
            intent.putExtra("bookPages", selectedBook.getPages());
            startActivity(intent);
        });

        listViewBooks.setOnItemLongClickListener((adapterView, view, position, id) -> {
            bookList.remove(position);
            bookAdapter.notifyDataSetChanged();
            return true;
        });
    }
}