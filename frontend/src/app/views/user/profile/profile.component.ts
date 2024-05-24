import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../service/user.service';
import { UserDto } from '../../../model/user-dto.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user1: UserDto | null = null;

  constructor(private requestsService: UserService) {}

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId) {
      this.requestsService.getUserById(userId).subscribe(
        (user: UserDto) => {
          this.user1 = user;
          console.log(this.user1);
        },
        error => {
          console.error('Error fetching user', error);
        }
      );
    }
  }
}
