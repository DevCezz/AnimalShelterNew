import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { TranslocoMessageFormatModule } from "@ngneat/transloco-messageformat";
import { AppComponent } from "./app.component";
import { CoreModule } from "@core/core.module";
import { AnimalsModule } from "@animals/animals.module";
import { HttpClientModule } from "@angular/common/http";
import { TranslocoRootModule } from "@transloco/transloco-root.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CoreModule,
    BrowserModule,
    AnimalsModule,
    HttpClientModule,
    TranslocoRootModule,
    TranslocoMessageFormatModule.init()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
