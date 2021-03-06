import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { GetAnimalDetails, GetAnimalsRecord } from "@animals/services/types/http-animals.type";

@Injectable({
  providedIn: 'root'
})
export class AnimalsHttpService {

  constructor(
    private httpClient: HttpClient
  ) {}

  getAnimals() {
    return this.httpClient.get<GetAnimalsRecord[]>('/shelter/animals');
  }

  getAnimalDetails(animalId: number) {
    return this.httpClient.get<GetAnimalDetails>(`/shelter/animals/${animalId}`);
  }
}
