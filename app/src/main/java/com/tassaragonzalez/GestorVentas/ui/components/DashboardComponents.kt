import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SalesAnalyticsCard(percentage: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 1500, delayMillis = 300),
        label = "salesAnimation"
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Análisis de Ventas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "${(percentage * 100).toInt()}% DE LA META",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                val liquidColors = listOf(Color(0xFF00C853), Color(0xFFA5D6A7))
                CylinderProgressBar(
                    modifier = Modifier.size(width = 50.dp, height = 90.dp),
                    progress = animatedProgress,
                    fillColors = liquidColors
                )
            }
        }
    }
}

@Composable
fun CylinderProgressBar(modifier: Modifier = Modifier, progress: Float, fillColors: List<Color>, containerColor: Color = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f), borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) {
    Canvas(modifier=modifier){val strokeWidth=2.dp.toPx();val ellipseHeight=16.dp.toPx();val bodyHeight=size.height-ellipseHeight;drawRect(color=containerColor,topLeft=Offset(x=0f,y=ellipseHeight/2),size=Size(width=size.width,height=bodyHeight));drawOval(color=containerColor,topLeft=Offset(x=0f,y=size.height-ellipseHeight),size=Size(width=size.width,height=ellipseHeight));val numLines=3;for(i in 1..numLines){val y=(bodyHeight/(numLines+1))*i+(ellipseHeight/2);drawLine(color=borderColor.copy(alpha=0.5f),start=Offset(x=strokeWidth,y=y),end=Offset(x=size.width-strokeWidth,y=y),strokeWidth=1.dp.toPx())};drawLine(color=borderColor,start=Offset(x=strokeWidth/2,y=ellipseHeight/2),end=Offset(x=strokeWidth/2,y=bodyHeight+ellipseHeight/2),strokeWidth=strokeWidth);drawLine(color=borderColor,start=Offset(x=size.width-strokeWidth/2,y=ellipseHeight/2),end=Offset(x=size.width-strokeWidth/2,y=bodyHeight+ellipseHeight/2),strokeWidth=strokeWidth);drawOval(color=borderColor,topLeft=Offset(x=0f,y=0f),size=Size(width=size.width,height=ellipseHeight),style=Stroke(width=strokeWidth));drawOval(color=borderColor,topLeft=Offset(x=0f,y=size.height-ellipseHeight),size=Size(width=size.width,height=ellipseHeight),style=Stroke(width=strokeWidth));val fillHeight=(size.height-ellipseHeight)*progress.coerceIn(0f,1f);val topOffset=(size.height-ellipseHeight)*(1f-progress.coerceIn(0f,1f))+(ellipseHeight/2);if(fillHeight>0){val inset=strokeWidth;val transparentFillColors=fillColors.map{it.copy(alpha=0.7f)};val gradientBrush=Brush.verticalGradient(colors=transparentFillColors,startY=topOffset,endY=size.height);drawRect(brush=gradientBrush,topLeft=Offset(x=inset,y=topOffset),size=Size(width=size.width-(inset*2),height=fillHeight));drawOval(brush=gradientBrush,topLeft=Offset(x=inset,y=size.height-ellipseHeight),size=Size(width=size.width-(inset*2),height=ellipseHeight));drawOval(brush=gradientBrush,topLeft=Offset(x=inset,y=topOffset-(ellipseHeight/2)),size=Size(width=size.width-(inset*2),height=ellipseHeight))}}
}