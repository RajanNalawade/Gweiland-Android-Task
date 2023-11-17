import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}

fun Double.formatDollarPrice(): String {
    val dec = DecimalFormat("#,###.##")
    return dec.format(this) ?: ""
}