import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


 class Table6Db : SugarRecord{
	var bankid: String? = null
		private set
	var bankcode: String? = null
		private set
	var bankname: String? = null
		private set
	var banktype: String? = null
		private set
	var ifsccode: String? = null
		private set
	 var banknamehindi: String? = null
		 private set



	constructor()
	 constructor(
		 bankid: String?,
		 bankcode: String?,
		 bankname: String?,
		 banktype: String?,
		 ifsccode: String?,
		 banknamehindi: String?
	 ) : super() {
		 this.bankid = bankid
		 this.bankcode = bankcode
		 this.bankname = bankname
		 this.banktype = banktype
		 this.ifsccode = ifsccode
		 this.banknamehindi = banknamehindi
	 }
	 override fun toString(): String {
		 return bankname.toString() // What to display in the Spinner list.
	 }

 }
