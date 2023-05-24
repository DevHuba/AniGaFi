package eu.devhuba.anigafi.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.devhuba.anigafi.R

val V18Rounded = FontFamily(
	Font(R.font.vag18_rounded_m_normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
	h1 = TextStyle(
		fontFamily = V18Rounded, fontWeight = FontWeight.Normal, fontSize = 22.sp
	),
	
	h2 = TextStyle(
		fontFamily = V18Rounded, fontWeight = FontWeight.Normal, fontSize = 16.sp
	),
	
	h3 = TextStyle(
		fontFamily = V18Rounded, fontWeight = FontWeight.Bold, fontSize = 14.sp
	),
	
	body1 = TextStyle(
		fontFamily = V18Rounded, fontWeight = FontWeight.Normal, fontSize = 14.sp
	)

)

