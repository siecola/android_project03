package br.com.siecola.androidproject03.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.siecola.androidproject03.MainActivity
import br.com.siecola.androidproject03.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "FCMService"

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        //Here you should update your back-end server with the new Firebase Cloud Messaging token
        Log.d(TAG, "FCM token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Payload: " + remoteMessage.data)
            if (remoteMessage.data.containsKey("product")) {
                sendProductNotification(remoteMessage.data.get("product")!!)
            }

            if (remoteMessage.data.containsKey("salesMessage")) {
                sendSalesMessage(remoteMessage.data.get("salesMessage")!!)
            }
        }
    }

    private fun sendSalesMessage(salesMessage: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("salesMessage", salesMessage)
        sendNotification(intent)
    }

    private fun sendProductNotification(productInfo: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("product", productInfo)
        sendNotification(intent)
    }

    private fun sendNotification(intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "1"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_cloud_queue_black_24dp)
            .setContentTitle("Sales Message")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Sales provider",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}